package com.turvo.bankingqueue.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turvo.bankingqueue.entity.CounterService;

@Repository
public interface CounterServiceRepository extends CrudRepository<CounterService, Integer> {

}
