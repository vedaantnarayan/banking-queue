package com.turvo.bankingqueue.exception;

import java.time.LocalDateTime;

/**
 * Wrapper containing exception/error info usable for client when handled in the application. 
 *  
 * @author vedantn
 *
 */
public class ExceptionJSONInfo {
	private int statusCode;
	private String message;
	private String url;
	private LocalDateTime time = LocalDateTime.now();

	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
}
