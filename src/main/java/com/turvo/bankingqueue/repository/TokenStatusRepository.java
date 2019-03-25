package com.turvo.bankingqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turvo.bankingqueue.entity.TokenStatus;

@Repository
public interface TokenStatusRepository extends CrudRepository<TokenStatus, Integer> {
	@Modifying
	@Query(value = "insert into token_status(token_id, counter_id) values(?1,?2)", nativeQuery = true)
	void insertTokenStatus(Integer tokenId, Integer counterId);

	@Modifying
	@Query("update TokenStatus ts set ts.inProgress = ?3 where ts.counter.counterId = ?2 and ts.token.tokenId=?1")
	int updateTokenStatus(Integer tokenId, Integer counterId, boolean b);

	@Query("select DISTINCT ts.token.tokenId from TokenStatus ts where ts.counter.counterId=?1 and ts.inProgress=true")
	List<Integer> listOfInProgressTokenId(Integer counterId);
}
