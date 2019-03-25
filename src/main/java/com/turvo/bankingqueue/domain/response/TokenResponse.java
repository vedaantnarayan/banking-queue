/**
 * 
 */
package com.turvo.bankingqueue.domain.response;

import com.turvo.bankingqueue.domain.request.TokenRequest;

/**
 * @author vedantn
 *
 */
public class TokenResponse {

	private Integer tokenId;
	private Integer customerId;
	private String customerName;
	private String tokenPriority;
	private String comments;
	private String status;
	private Integer counterId;
	private String tokenTypename;
	
	public TokenResponse() {
		
	}
	public TokenResponse(Integer tokenId, Integer customerId, String customerName, String tokenPriority,
			String comments, String status, Integer counterId, String tokenTypename) {
		super();
		this.tokenId = tokenId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.tokenPriority = tokenPriority;
		this.comments = comments;
		this.status = status;
		this.counterId = counterId;
		this.tokenTypename = tokenTypename;
	}
	public TokenResponse(TokenRequest tokenRequest) {
		this.tokenId = tokenRequest.getTokenId();
		this.customerId = tokenRequest.getCustomerId();
		if(tokenRequest.getServicePriority() != null)
		this.tokenPriority = tokenRequest.getServicePriority().name();
		this.comments = tokenRequest.getComments();
		if(tokenRequest.getStatus() != null)
		this.status = tokenRequest.getStatus().name();
		this.counterId = tokenRequest.getCounterId();
		if(tokenRequest.getTokenType() != null)
		this.tokenTypename = tokenRequest.getTokenType().name();
		
	}
	public Integer getTokenId() {
		return tokenId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getTokenPriority() {
		return tokenPriority;
	}
	public String getComments() {
		return comments;
	}
	public String getStatus() {
		return status;
	}
	public Integer getCounterId() {
		return counterId;
	}
	public String getTokenTypename() {
		return tokenTypename;
	}
	
}
