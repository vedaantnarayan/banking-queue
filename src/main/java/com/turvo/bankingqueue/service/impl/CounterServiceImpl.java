package com.turvo.bankingqueue.service.impl;

import static com.turvo.bankingqueue.constant.ProcessTimeConstants.CHECK_DEPOSIT_TIME_SEC;
import static com.turvo.bankingqueue.constant.ProcessTimeConstants.MANAGER_APPROVAL_TIME_IN_SEC;
import static com.turvo.bankingqueue.constant.ProcessTimeConstants.WITHDRAW_TIME_IN_SEC;
import static com.turvo.bankingqueue.constant.ServiceType.ACC_CLOSE;
import static com.turvo.bankingqueue.constant.ServiceType.ACC_OPEN;
import static com.turvo.bankingqueue.constant.ServiceType.ACC_VERIFICATION;
import static com.turvo.bankingqueue.constant.ServiceType.BALANCE_ENQUIRY;
import static com.turvo.bankingqueue.constant.ServiceType.CASH_DEPOSIT;
import static com.turvo.bankingqueue.constant.ServiceType.CASH_WITHDRAW;
import static com.turvo.bankingqueue.constant.ServiceType.CHECK_DEPOSIT;
import static com.turvo.bankingqueue.constant.ServiceType.DOC_VERIFICATION;
import static com.turvo.bankingqueue.constant.ServiceType.MANAGER_APPROVAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.turvo.bankingqueue.constant.CounterType;
import com.turvo.bankingqueue.constant.EmployeeRole;
import com.turvo.bankingqueue.constant.ServicePriority;
import com.turvo.bankingqueue.constant.ServiceType;
import com.turvo.bankingqueue.constant.TokenStatus;
import com.turvo.bankingqueue.constant.TokenTypeEnum;
import com.turvo.bankingqueue.domain.request.TokenRequest;
import com.turvo.bankingqueue.entity.Token;
import com.turvo.bankingqueue.exception.BranchNotExistsException;
import com.turvo.bankingqueue.exception.CountersNotAvailableException;
import com.turvo.bankingqueue.exception.InsufficientPrivilegesException;
import com.turvo.bankingqueue.helper.CounterDetails;
import com.turvo.bankingqueue.repository.BranchRepository;
import com.turvo.bankingqueue.repository.CounterRepository;
import com.turvo.bankingqueue.repository.ServiceRepository;
import com.turvo.bankingqueue.repository.TokenStatusRepository;
import com.turvo.bankingqueue.service.CounterService;
import com.turvo.bankingqueue.service.EmployeeService;
import com.turvo.bankingqueue.service.TokenService;

@SuppressWarnings("rawtypes")
@Component
public class CounterServiceImpl implements CounterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CounterServiceImpl.class);

	private List<CounterDeskImpl> counterList = new ArrayList<>();

	@Autowired
	protected CounterService counterService;

	@Autowired
	private CounterRepository counterRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private BranchRepository branchRepository;
	
	@Autowired
	private TokenStatusRepository tokenStatusRepository;
	 
	@Autowired
	private ServiceRepository serviceRepository;

	ExecutorService executor;

	@EventListener(ApplicationReadyEvent.class)
	public void initCounters() {
		List<CounterDetails> counterDetailsList = getAvailableCounters();

		if (counterDetailsList.isEmpty()) {
			throw new RuntimeException("CounterDetails not available exception");
		}
		executor = Executors.newFixedThreadPool(counterDetailsList.size());
		for (CounterDetails counterDetails : counterDetailsList) {
			List<ServiceType> serviceTypes = getServiceTypeList(counterDetails.getEmployeeId());
			CounterDeskImpl counterDesk = new CounterDeskImpl(counterService, counterDetails,
					counterDetails.getEmployeeId(), CounterType.valueOf(counterDetails.getCounterType()), serviceTypes);
			executor.execute(counterDesk);
			counterList.add(counterDesk);
		}
	}

	private List<CounterDetails> getAvailableCounters() {
		List<CounterDetails> counterIdList = counterRepository.getAvailableCounters();
		return this.getCounterDetails(counterIdList);
	}

	@Override
	public Token assignTokenToCounter(Token token,TokenRequest tokenRequest) {
		return assignToken(token,tokenRequest);
	}

	@Override
	public List<CounterDetails> getCounterStatus() {
		return getAvailableCounters();
	}

	private Token assignToken(Token token,TokenRequest tokenRequest) {
		Queue<Enum> actionItem = actionItems(token.getTokenType().getName());
		List<CounterDeskImpl> counterDesks = getAvailableCounterDesks(token,actionItem);
		if (counterDesks.isEmpty()) {
			throw new CountersNotAvailableException();
		}
		Integer minQueueLength = Integer.MAX_VALUE;
		CounterDeskImpl minCounterDesk = counterDesks.get(0);
		for (CounterDeskImpl counterDesk : counterDesks) {
			int curMinLength = counterDesk.getMinQueueLength(token.getTokenPriority());
			if (minQueueLength > curMinLength) {
				minQueueLength = curMinLength;
				minCounterDesk = counterDesk;
				if (minQueueLength == 0) {
					// found empty queue, no need to search other queues
					break;
				}
			}
		}

		addTokenToCounterQueue(minCounterDesk, token);
		tokenService.updateTokenStatus(token.getTokenId(), TokenStatus.QUEUED.name());
		if(tokenRequest != null) {
			tokenRequest.setCounterId(minCounterDesk.getCounterId());
			tokenRequest.setStatus(TokenStatus.valueOf(token.getStatus()));
		}
		return token;

	}

	private Queue<Enum> actionItems(String serviceType) {
		return TokenTypeEnum.valueOf(serviceType).getActionTimes();
	}
	
	private void addTokenToCounterQueue(CounterDeskImpl counter, Token token) {
		counter.addTokenToQueue(token);
		tokenService.addTokenToCounter(token.getTokenId(), counter.getCounterId());
	}

	private List<CounterDeskImpl> getAvailableCounterDesks(Token token,Queue<Enum> actionItem) {
		if (token.getTokenPriority() == ServicePriority.PREMIUM.name()) {
			return counterList.stream().filter(counterDesk -> counterDesk.getCounterType() == CounterType.BOTH
					|| counterDesk.getCounterType() == CounterType.PREMIUM).filter(counterDesk -> {
						return counterDesk.getServiceTypes().contains(actionItem.peek());
					}).collect(Collectors.toList());
		} else {
			return counterList.stream().filter(counterDesk -> counterDesk.getCounterType() == CounterType.BOTH
					|| counterDesk.getCounterType() == CounterType.REGULAR).filter(counterDesk -> {
						return counterDesk.getServiceTypes().contains(actionItem.peek());
					}).collect(Collectors.toList());
		}
	}

	public List<ServiceType> getServiceTypeList(int employeeId) {
		EmployeeRole role = employeeService.getEmployeeRole(employeeId);
		switch (role) {
		case MANAGER:
			return Arrays.asList(ACC_VERIFICATION, BALANCE_ENQUIRY, ACC_CLOSE, ACC_OPEN, MANAGER_APPROVAL,
					DOC_VERIFICATION);
		case OPERATOR:
			return Arrays.asList(ACC_VERIFICATION, BALANCE_ENQUIRY, CASH_WITHDRAW, CASH_DEPOSIT, CHECK_DEPOSIT,
					DOC_VERIFICATION);
		default:
			throw new RuntimeException("Unknown EmployeeRole :" + role.name());
		}

	}

	@Override
	@Transactional
	public Token serveToken(Token token, CounterDeskImpl counterDesk) {
		Queue<Enum> actionItem = actionItems(token.getTokenType().getName());
		
		while (counterDesk.getServiceTypes().contains(actionItem.peek())) {
			Enum serviceType = actionItem.poll();
			if (serviceType == null) {
				token.setStatus(TokenStatus.COMPLETED.name());
				updateTokenStatus(token.getTokenId(), TokenStatus.COMPLETED, counterDesk.getEmpId());
				break;
			}
			LOGGER.info("CounterId:{}, TokenId:{}, Processing Request:{}", counterDesk.getCounterId(),
					token.getTokenId(), serviceType.name());
			processRequest((ServiceType) serviceType);
		}
		if (actionItem.size() > 0) {
			token = counterService.assignTokenToCounter(token,null);
			updateTokenComments(token.getTokenId(), "Redirecting to counter Id:" + counterDesk.getCounterId());
		} else {
			token.setStatus(TokenStatus.COMPLETED.name());
			updateTokenStatus(token.getTokenId(), TokenStatus.COMPLETED, counterDesk.getEmpId());
			updateTokenComments(token.getTokenId(),
					"Completed processing token at counter id:" + counterDesk.getCounterId());
		}
		tokenService.updateTokenQueueStatus(token.getTokenId(), counterDesk.getCounterId(), false);
		return token;
	}

	@Override
	public Boolean updateTokenStatus(Integer tokenId, TokenStatus tokenStatus, Integer emplId) {
		if (tokenStatus == TokenStatus.COMPLETED || tokenStatus == TokenStatus.CANCELLED) {
			EmployeeRole role = employeeService.getEmployeeRole(emplId);
			if (role == EmployeeRole.MANAGER || role == EmployeeRole.OPERATOR) {
				tokenService.updateTokenStatus(tokenId, tokenStatus.name());
			} else {
				throw new InsufficientPrivilegesException(
						"Only MANAGER or OPERATOR have privileges to " + tokenStatus.name() + " the token");
			}

		} else {
			tokenService.updateTokenStatus(tokenId, tokenStatus.name());
		}
		return true;
	}

	@Override
	public Token updateTokenComments(Integer tokenId, String comments) {
		return tokenService.updateTokenComments(tokenId, comments);
	}

	@Override
	public List<CounterDetails> getCounterStatus(String branchName) {
		if (!branchRepository.existsByBranchName(branchName)) {
			throw new BranchNotExistsException("No Branch found with name:" + branchName);
		}
		List<CounterDetails> counterIdList = counterRepository.getAvailableCountersByBranch(branchName);
		return this.getCounterDetails(counterIdList);
	}

	private List<CounterDetails> getCounterDetails(List<CounterDetails> counterIdList) {
		List<CounterDetails> counterDetailsList = new ArrayList<CounterDetails>();
		for (CounterDetails counterDetails : counterIdList) {
			counterDetails.setTokenIdList(tokenStatusRepository.listOfInProgressTokenId(counterDetails.getCounterId()));
			counterDetails.setServiceTypes(serviceRepository.findCounterServices(counterDetails.getCounterId()));
			counterDetails.setActive("1");
			counterDetailsList.add(counterDetails);
		}

		return counterDetailsList;

	}

	private void processRequest(ServiceType serviceType) {
		switch (serviceType) {
		case ACC_VERIFICATION:
		case BALANCE_ENQUIRY:
		case CASH_WITHDRAW:
		case CASH_DEPOSIT:
		case ACC_CLOSE:
		case ACC_OPEN:
		case DOC_VERIFICATION:
			// process the request
			LOGGER.info("Processing {} request, will be completed in 1 min", serviceType.name());
			waitInSec(WITHDRAW_TIME_IN_SEC);
			break;
		case CHECK_DEPOSIT:
			// process the request
			LOGGER.info("Processing {} request, will be completed in 1 min", serviceType.name());
			waitInSec(CHECK_DEPOSIT_TIME_SEC);
			break;
		case MANAGER_APPROVAL:
			// process the request
			LOGGER.info("Processing {} request, will be completed in 2 mins", serviceType.name());
			waitInSec(MANAGER_APPROVAL_TIME_IN_SEC);
			break;
		default:
			LOGGER.error("Unknown ServiceType: {}", serviceType.name());
		}
	}

	private void waitInSec(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
