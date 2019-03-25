package com.turvo.bankingqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turvo.bankingqueue.constant.ServiceType;
import com.turvo.bankingqueue.entity.Service;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Integer> {
	
	@Query("select name from Service where serviceId in (select service.serviceId from CounterService where counter.counterId=?1)")
	List<ServiceType> findCounterServices(Integer counterId);
}
