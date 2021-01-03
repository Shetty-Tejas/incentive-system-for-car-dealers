package com.groupthree.incentivesystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IncentiveSystemExceptionHandler {

	@ResponseBody
	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<?> handleException(ValidationException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMsg());
	}
	
	@ResponseBody
	@ExceptionHandler(value = LoginException.class)
	public ResponseEntity<?> handleException(LoginException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMsg());
	}
	
	@ResponseBody
	@ExceptionHandler(value = FetchEmptyException.class)
	public ResponseEntity<?> handleException(FetchEmptyException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMsg());
	}
}
