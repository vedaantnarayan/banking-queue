package com.turvo.bankingqueue.service.impl;

import java.util.List;

import com.turvo.bankingqueue.constant.CounterType;
import com.turvo.bankingqueue.constant.ServiceType;
import com.turvo.bankingqueue.constant.TokenStatus;
import com.turvo.bankingqueue.entity.Token;
import com.turvo.bankingqueue.exception.EmptyCounterQueueException;
import com.turvo.bankingqueue.exception.InvalidTokenException;
import com.turvo.bankingqueue.helper.CounterDetails;
import com.turvo.bankingqueue.helper.CounterQueue;
import com.turvo.bankingqueue.service.CounterService;

public class CounterDeskImpl extends Thread {

	private CounterService counterService;
	private CounterQueue counterQueue;
	private int empId;
	private CounterDetails counterDetails;
	private CounterType counterType;
	private List<ServiceType> serviceTypes;

	public CounterDeskImpl(CounterService counterService, CounterDetails counterDetails, int empId,
			CounterType counterType, List<ServiceType> serviceTypes) {
		this.counterService = counterService;
		this.counterDetails = counterDetails;
		this.empId = empId;
		this.counterType = counterType;
		this.counterQueue = new CounterQueue(counterType);
		this.serviceTypes = serviceTypes;
	}

	public CounterDetails getCounterDetails() {
		return counterDetails;
	}

	public void setCounterDetails(CounterDetails counterDetails) {
		this.counterDetails = counterDetails;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public CounterType getCounterType() {
		return counterType;
	}

	public void setCounterType(CounterType counterType) {
		this.counterType = counterType;
	}

	public Integer getQueueLength() {
		return counterQueue.getQueueLength();
	}

	public int getCounterId() {
		return counterDetails.getCounterId();
	}

	public Integer getMinQueueLength(String priority) {
		return counterQueue.getMinQueueLength(priority);
	}

	public Token addTokenToQueue(Token token) {
		return counterQueue.addTokenToQueue(token);
	}

	private Token serveToken(Token token) {
		Token servedToken = null;
		if (token != null) {
			if (TokenStatus.CANCELLED.name().equals(token.getStatus())
					|| TokenStatus.COMPLETED.name().equals(token.getStatus())) {
				throw new InvalidTokenException("Can't process " + token.getStatus() + "token");
			}
			servedToken = counterService.serveToken(token, this);
			counterQueue.addToRecentServedList(servedToken);
		}
		return servedToken;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Token token = counterQueue.fetchToken();
				serveToken(token);
			} catch (EmptyCounterQueueException e) {
				// ignore
			}
		}
	}

	public List<ServiceType> getServiceTypes() {
		return serviceTypes;
	}

	public void setServiceTypes(List<ServiceType> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}

}
