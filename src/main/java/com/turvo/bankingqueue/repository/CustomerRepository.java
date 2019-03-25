package com.turvo.bankingqueue.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.turvo.bankingqueue.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	boolean existsByName(String name);

	@Query("SELECT c.customerId FROM Customer c where c.name = ?1 ")
	public Integer fetchCustomerIdByName(String name);

}
