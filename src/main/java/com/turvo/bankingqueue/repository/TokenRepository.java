package com.turvo.bankingqueue.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turvo.bankingqueue.domain.response.TokenResponse;
import com.turvo.bankingqueue.entity.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {
	
	@Query("select new com.turvo.bankingqueue.domain.response.TokenResponse(ts.token.tokenId,ts.token.customer.customerId,ts.token.customer.name,ts.token.tokenPriority"
			+ ",ts.token.comment,ts.token.status,ts.counter.counterId,ts.token.tokenType.name )"
			+ " from TokenStatus ts where ts.token.tokenId=?1 group by ts.token.tokenId ")
	TokenResponse fetchToken(Integer tokenId);
}
