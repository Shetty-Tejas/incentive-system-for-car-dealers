package com.groupthree.incentivesystem.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class IncentiveSystemExceptionHandler {

	@ControllerAdvice
	public class IncentiveystemExceptionHandler extends ResponseEntityExceptionHandler{
		
		@ExceptionHandler(value = {Exception.class})
		public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request){
			return new ResponseEntity<Object>(ex, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
