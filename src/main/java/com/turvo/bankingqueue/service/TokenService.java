/**
 * 
 */
package com.turvo.bankingqueue.service;

import com.turvo.bankingqueue.domain.request.TokenRequest;
import com.turvo.bankingqueue.domain.response.TokenResponse;
import com.turvo.bankingqueue.entity.Token;
import com.turvo.bankingqueue.exception.TokenException;

/**
 * @author vedantn
 *
 */
public interface TokenService {

	TokenResponse createAndAssignToken(TokenRequest tokenRequest) throws TokenException;

	Token updateTokenStatus(Integer tokenId, String name);

	void addTokenToCounter(Integer tokenId, int counterId);

	Token updateTokenComments(Integer tokenId, String comments);

	void updateTokenQueueStatus(Integer tokenId, int counterId, boolean b);

	TokenResponse fetchToken(Integer tokenId);

}
