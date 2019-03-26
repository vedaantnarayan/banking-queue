package com.turvo.bankingqueue.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ExceptionJSONInfo handleIllegalArguementException
		(HttpServletRequest request, Exception ex){		
		return response(request, ex,HttpStatus.BAD_REQUEST.value());
	}

	private ExceptionJSONInfo response(HttpServletRequest request, Exception ex, int statusCode) {
		ExceptionJSONInfo response = new ExceptionJSONInfo();
		response.setUrl(request.getRequestURL().toString());
		response.setMessage(ex.getMessage());
		response.setStatusCode(statusCode);
		return response;
	}
	
	@ExceptionHandler
	public ExceptionJSONInfo handleTokenGenerationException
		(HttpServletRequest request, Exception ex){		
		return response(request, ex,HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
}
