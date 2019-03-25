package com.turvo.bankingqueue.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turvo.bankingqueue.entity.Branch;

@Repository
public interface BranchRepository extends CrudRepository<Branch, Integer> {

	boolean existsByBranchName(String branchName);
	Branch findByBranchName(String branchName);

	
}
