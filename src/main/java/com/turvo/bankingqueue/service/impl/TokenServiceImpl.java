/**
 * 
 */
package com.turvo.bankingqueue.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turvo.bankingqueue.domain.request.TokenRequest;
import com.turvo.bankingqueue.domain.response.TokenResponse;
import com.turvo.bankingqueue.entity.Customer;
import com.turvo.bankingqueue.entity.Token;
import com.turvo.bankingqueue.exception.BranchNotExistsException;
import com.turvo.bankingqueue.exception.InvalidTokenException;
import com.turvo.bankingqueue.exception.TokenException;
import com.turvo.bankingqueue.repository.BranchRepository;
import com.turvo.bankingqueue.repository.CustomerRepository;
import com.turvo.bankingqueue.repository.TokenRepository;
import com.turvo.bankingqueue.repository.TokenStatusRepository;
import com.turvo.bankingqueue.repository.TokenTypeRepository;
import com.turvo.bankingqueue.service.CounterService;
import com.turvo.bankingqueue.service.CustomerService;
import com.turvo.bankingqueue.service.TokenService;

/**
 * @author vedantn
 *
 */
@Service
public class TokenServiceImpl implements TokenService {
	

    @Autowired
    private CounterService counterService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BranchRepository branchRepository;
    
    @Autowired
    private TokenRepository tokenRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private TokenTypeRepository tokenTypeRepository;
    
    @Autowired
    private TokenStatusRepository tokenStatusRepository;


    @Transactional
    public TokenResponse createAndAssignToken(TokenRequest tokenRequest) throws TokenException {
        try {
        	
            if(!branchRepository.existsByBranchName(tokenRequest.getBranchName())) {
                throw new BranchNotExistsException("No branch exist with branchName:"+tokenRequest.getBranchName());
            }
            createCustomerIfNotExist(tokenRequest);
            Token createdToken = createToken(tokenRequest);
            counterService.assignTokenToCounter(createdToken,tokenRequest);
            
        } catch(Exception e) {
            throw new TokenException("Unable to create or assign Token "+e.getMessage(), e);
        }
        return new TokenResponse(tokenRequest);
    }

	private TokenRequest createCustomerIfNotExist(TokenRequest tokenRequest) {
		Integer customerId = customerService.fetchCustomerIdByName(tokenRequest.getCustomer().getName());
		if (customerId == null) {
            customerService.createCustomer(tokenRequest);
        }else {
        	tokenRequest.setCustomerId(customerId);
        }
		return tokenRequest;
	}

    private Token createToken(TokenRequest tokenRequest) {
		
    	Token token = new Token();
    	token.setBranch(branchRepository.findByBranchName(tokenRequest.getBranchName()));
    	Optional<Customer> customerOpt = customerRepository.findById(tokenRequest.getCustomerId());
    	if(customerOpt.isPresent())
    	token.setCustomer(customerOpt.get());
    	token.setTokenType(tokenTypeRepository.findByName(tokenRequest.getTokenType().name()));
    	token.setTokenPriority(tokenRequest.getServicePriority().name());
    	tokenRepository.save(token);
    	
    	tokenRequest.setTokenId(token.getTokenId());
		return token;
	}

	@Override
	public Token updateTokenStatus(Integer tokenId, String status) {
		Token token = token(tokenId);
		if (token != null) {
			token.setStatus(status);
			tokenRepository.save(token);
		}
		return token;
	}

	private Token token(Integer tokenId) {
		Token token = null;
		Optional<Token> tokenOpt = tokenRepository.findById(tokenId);
    	if(tokenOpt.isPresent()) {
    		token = tokenOpt.get();
    	}
    	return token; 
	}

	@Override
	public void addTokenToCounter(Integer tokenId, int counterId) {
		tokenStatusRepository.insertTokenStatus(tokenId, counterId);
	}

	@Override
	public Token updateTokenComments(Integer tokenId, String comments) {
		Token token = token(tokenId);
		if (token != null) {
			token.setComment(comments);
			tokenRepository.save(token);
		}
		return token;
	}

	@Override
	public void updateTokenQueueStatus(Integer tokenId, int counterId, boolean b) {
		tokenStatusRepository.updateTokenStatus(tokenId, counterId, b);
		
	}
	
	@Override
    public TokenResponse fetchToken(Integer tokenId) {
        if (tokenId <= 0) {
            throw new InvalidTokenException("Invalid tokenId:" + tokenId);
        }
        return tokenRepository.fetchToken(tokenId);
    }

}
