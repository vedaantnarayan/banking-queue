/**
 * 
 */
package com.turvo.bankingqueue.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.turvo.bankingqueue.domain.request.TokenRequest;
import com.turvo.bankingqueue.domain.response.TokenResponse;
import com.turvo.bankingqueue.exception.TokenException;
import com.turvo.bankingqueue.service.TokenService;

/**
 * @author vedantn
 *
 */
@RestController
public class TokenController {
	
	private static final String PATH_PARAM_TOKEN_ID = "/{tokenId}";
	static final String URI_TOKEN = "/token";
	static final String BASE_URI = URI_TOKEN + PATH_PARAM_TOKEN_ID;
	
	@Autowired
	TokenService tokenService;

	/**
	 * Creates Token, If the customer not exist then will create new customer before creating token
	 *
	 * @throws InvalidCustomerException if provided customerId not valid
	 * @throws TokenException if token is not created or assigned to counter
	 * @return TokenResponse
	 */
    
	@PostMapping(URI_TOKEN)
	public ResponseEntity<TokenResponse> createToken(@RequestBody TokenRequest token) {
		return new ResponseEntity<>(tokenService.createAndAssignToken(token), HttpStatus.OK);
	}

	/**
	 * Returns Token for given tokenId
	 *
	 * @param tokenId
	 * @return TokenResponse
	 * throws InvalidTokenException if tokenId is invalid
	 */
	@GetMapping(BASE_URI)
	public ResponseEntity<TokenResponse> fetchToken(@PathVariable Integer tokenId) {
		return new ResponseEntity<>(tokenService.fetchToken(tokenId), HttpStatus.OK);
	
	}
	

}
