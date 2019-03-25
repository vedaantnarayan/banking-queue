package com.turvo.bankingqueue.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turvo.bankingqueue.entity.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
	
}
