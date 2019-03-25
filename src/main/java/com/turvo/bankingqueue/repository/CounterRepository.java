package com.turvo.bankingqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turvo.bankingqueue.entity.Counter;
import com.turvo.bankingqueue.helper.CounterDetails;

@Repository
public interface CounterRepository extends CrudRepository<Counter, Integer> {

	@Query("select new com.turvo.bankingqueue.helper.CounterDetails(c.counterId, c.employee.employeeId, c.active, c.counterType, c.branch.branchName,c.employee.name) "
            + "from Counter c where UPPER(c.branch.branchName) like UPPER(?1) and c.active=1")
	List<CounterDetails> getAvailableCountersByBranch(String branchName);
	
	@Query("select new com.turvo.bankingqueue.helper.CounterDetails(c.counterId, c.employee.employeeId, c.active, c.counterType, c.branch.branchName,c.employee.name) from Counter c where c.active=1")
	List<CounterDetails> getAvailableCounters();
}
