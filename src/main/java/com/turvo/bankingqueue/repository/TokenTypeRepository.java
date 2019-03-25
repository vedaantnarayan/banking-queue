package com.turvo.bankingqueue.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turvo.bankingqueue.entity.TokenType;

@Repository
public interface TokenTypeRepository extends CrudRepository<TokenType, Integer> {
	TokenType findByName(String name);

}
