package com.turvo.bankingqueue.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.turvo.bankingqueue.domain.request.TokenRequest;

@Component
public interface CustomerService {
	
	Boolean isCustomerExist(String customerName);

	@Transactional
	TokenRequest createCustomer(TokenRequest tokenRequest);
	
	Integer fetchCustomerIdByName(String customerName);

}
