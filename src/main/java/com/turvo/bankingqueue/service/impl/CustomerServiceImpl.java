package com.turvo.bankingqueue.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turvo.bankingqueue.domain.request.CustomerRequest;
import com.turvo.bankingqueue.domain.request.TokenRequest;
import com.turvo.bankingqueue.entity.Address;
import com.turvo.bankingqueue.entity.Customer;
import com.turvo.bankingqueue.exception.CustomerException;
import com.turvo.bankingqueue.repository.AddressRepository;
import com.turvo.bankingqueue.repository.CustomerRepository;
import com.turvo.bankingqueue.service.CustomerService;

@Service
public class CustomerServiceImpl implements  CustomerService{


    @Autowired
    private CustomerRepository customerRepository;
   
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Boolean isCustomerExist(String customerName) {
        return customerRepository.existsByName(customerName);
    }

    @Override
    public TokenRequest createCustomer(TokenRequest tokenRequest) {
    	Customer customer = new Customer();
    	Address address = new Address();
    	customer(tokenRequest, customer, address);
    	
        if(customer.getName() == null ) {
            throw new CustomerException("Invalid customer name provided");
        }
        if(customer.getAddress() == null) {
            throw new CustomerException("Invalid customer address provided");
        }
        addressRepository.save(address);
        Customer customerCreated = customerRepository.save(customer);
        tokenRequest.setCustomerId(customerCreated.getCustomerId());
        return tokenRequest;
    }

	private void customer(TokenRequest tokenRequest, Customer customer, Address address) {
		
		CustomerRequest customerRequest = tokenRequest.getCustomer();
    	BeanUtils.copyProperties(customerRequest, customer);
    	BeanUtils.copyProperties(customerRequest.getAddress(), address);
    	customer.setAddress(address);
	}

	@Override
	public Integer fetchCustomerIdByName(String customerName) {
		return customerRepository.fetchCustomerIdByName(customerName);
	}

}
