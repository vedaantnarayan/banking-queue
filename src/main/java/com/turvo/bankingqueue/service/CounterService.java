package com.turvo.bankingqueue.service;

import java.util.List;

import com.turvo.bankingqueue.constant.TokenStatus;
import com.turvo.bankingqueue.domain.request.TokenRequest;
import com.turvo.bankingqueue.entity.Token;
import com.turvo.bankingqueue.helper.CounterDetails;
import com.turvo.bankingqueue.service.impl.CounterDeskImpl;

public interface CounterService {
	Token assignTokenToCounter(Token token,TokenRequest tokenRequest);

    List<CounterDetails> getCounterStatus();
    
    Token serveToken(Token token, CounterDeskImpl counterDesk);

	Boolean updateTokenStatus(Integer tokenId, TokenStatus tokenStatus, Integer emplId);

	Token updateTokenComments(Integer tokenId, String comment);

	List<CounterDetails> getCounterStatus(String branchName);
}
