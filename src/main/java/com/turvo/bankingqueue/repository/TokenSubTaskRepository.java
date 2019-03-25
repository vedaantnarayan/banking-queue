package com.turvo.bankingqueue.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turvo.bankingqueue.entity.TokenSubTask;

@Repository
public interface TokenSubTaskRepository extends CrudRepository<TokenSubTask, Integer> {

}
