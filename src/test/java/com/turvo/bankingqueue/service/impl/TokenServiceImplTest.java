
package com.turvo.bankingqueue.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.turvo.bankingqueue.api.BaseTest;
import com.turvo.bankingqueue.domain.request.TokenRequest;
import com.turvo.bankingqueue.domain.response.TokenResponse;
import com.turvo.bankingqueue.exception.InvalidTokenException;
import com.turvo.bankingqueue.exception.TokenException;
import com.turvo.bankingqueue.repository.BranchRepository;
import com.turvo.bankingqueue.repository.CustomerRepository;
import com.turvo.bankingqueue.repository.TokenRepository;
import com.turvo.bankingqueue.repository.TokenStatusRepository;
import com.turvo.bankingqueue.repository.TokenTypeRepository;

/**
 * @author vedantn
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TokenServiceImplTest extends BaseTest {

	@Mock
    private CounterServiceImpl counterService;

	@Mock
    private CustomerServiceImpl customerService;

	@Mock
    private BranchRepository branchRepository;
    
	@Mock
    private TokenRepository tokenRepository;
    
	@Mock
    private CustomerRepository customerRepository;
    
	@Mock
    private TokenTypeRepository tokenTypeRepository;
    
	@Mock
    private TokenStatusRepository tokenStatusRepository;
    
	@InjectMocks
	private TokenServiceImpl tokenServiceImpl;

	@Before
	public void setup() {
		initializeMocks(TokenServiceImplTest.class);
	}

	@Test
	public void test_fetchToken() {
	
		TokenRequest tokenRequest = defaultTokenRequest();
		TokenResponse tokenResponse = new TokenResponse(tokenRequest);
		
		when(tokenRepository.fetchToken(tokenRequest.getTokenId())).thenReturn(tokenResponse);

		TokenResponse tokenRes = tokenServiceImpl.fetchToken(tokenRequest.getTokenId());
		assertEquals(tokenResponse, tokenRes);
	}
	
	@Test(expected = InvalidTokenException.class)
	public void test_fetchToken_InvalidTokenException() {
	
		TokenRequest tokenRequest = defaultTokenRequest();
		
		when(tokenServiceImpl.fetchToken(0)).thenThrow(InvalidTokenException.class);
		tokenServiceImpl.fetchToken(tokenRequest.getTokenId());
	}

	@Test(expected = TokenException.class)
	public void test_ifBranchNotExist() {
		TokenRequest tokenRequest = defaultTokenRequest();
		
		when(tokenServiceImpl.createAndAssignToken(tokenRequest)).thenThrow(TokenException.class);
		tokenServiceImpl.createAndAssignToken(tokenRequest);

	}
	
/*	@Test
	public void test_createAndAssignToken() {
		TokenRequest tokenRequest = defaultTokenRequest();
		TokenResponse tokenResponse = new TokenResponse(tokenRequest);

		when(tokenServiceImpl.createAndAssignToken(tokenRequest)).thenReturn(tokenResponse);


	}*/

}
