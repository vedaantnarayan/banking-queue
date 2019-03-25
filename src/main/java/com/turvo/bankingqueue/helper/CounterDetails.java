package com.turvo.bankingqueue.helper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turvo.bankingqueue.constant.ServiceType;
import com.turvo.bankingqueue.entity.Token;

public class CounterDetails {

	private int counterId;
    @JsonIgnore
	private List<ServiceType> serviceTypes;

	private List<Integer> tokenIdList;

	private String isActive;

	private int employeeId;

	private String counterType;
	
	private String employeeName;

	private String branchName;

	
	public CounterDetails(){
		
	}
	
	public CounterDetails(int counterId, int employeeId,String isActive,String counterType, String branchName,String employeeName) {
		super();
		this.counterId = counterId;
		this.isActive = isActive;
		this.employeeId = employeeId;
		this.counterType = counterType;
		this.branchName = branchName;
		this.employeeName = employeeName;
	}

	public int getCounterId() {
		return counterId;
	}


	public void setCounterId(int counterId) {
		this.counterId = counterId;
	}


	public List<ServiceType> getServiceTypes() {
		return serviceTypes;
	}


	public void setServiceTypes(List<ServiceType> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}

	public String getActive() {
		return isActive;
	}

	public void setActive(String active) {
		isActive = active;
	}

	public List<Integer> getTokenIdList() {
		return tokenIdList;
	}

	public void setTokenIdList(List<Integer> tokenIdList) {
		this.tokenIdList = tokenIdList;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getCounterType() {
		return counterType;
	}

	public void setCounterType(String counterType) {
		this.counterType = counterType;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
}

class CounterStatus {
	List<Token> tokens;

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	
}
