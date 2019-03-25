/**
 * 
 */
package com.turvo.bankingqueue.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.turvo.bankingqueue.constant.ServicePriority;
import com.turvo.bankingqueue.constant.TokenStatus;
import com.turvo.bankingqueue.constant.TokenTypeEnum;

/**
 * @author vedantn
 *
 */
public class TokenRequest {

	private ServicePriority servicePriority;
	private Integer customerId;
	private TokenTypeEnum tokenType;
	private TokenStatus status;
	private Integer counterId;
	private Integer tokenId;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private CustomerRequest customer;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String branchName;
	@JsonIgnore
	private String comments;

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public ServicePriority getServicePriority() {
		return servicePriority;
	}

	public void setServicePriority(ServicePriority servicePriority) {
		this.servicePriority = servicePriority;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public CustomerRequest getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerRequest customer) {
		this.customer = customer;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public TokenStatus getStatus() {
		return status;
	}

	public void setStatus(TokenStatus status) {
		this.status = status;
	}

	public Integer getCounterId() {
		return counterId;
	}

	public void setCounterId(Integer counterId) {
		this.counterId = counterId;
	}

	public TokenTypeEnum getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenTypeEnum tokenType) {
		this.tokenType = tokenType;
	}

	public Integer getTokenId() {
		return tokenId;
	}

	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}
}
