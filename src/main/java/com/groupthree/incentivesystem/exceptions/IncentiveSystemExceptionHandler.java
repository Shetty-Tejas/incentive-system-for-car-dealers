package com.groupthree.incentivesystem.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IncentiveSystemExceptionHandler {
	
	private static final Logger E_LOGGER = LoggerFactory.getLogger("IncentiveSystemExceptionHandler.class"); 
	
	private final BodyBuilder response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
	
	@ResponseBody
	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<?> handleException(final ValidationException exception){
		E_LOGGER.error(exception.getMsg());
		return response.body(exception.getMsg());
	}
	
	@ResponseBody
	@ExceptionHandler(value = LoginException.class)
	public ResponseEntity<?> handleException(final LoginException exception){
		E_LOGGER.error(exception.getMsg());
		return response.body(exception.getMsg());
	}
	
	@ResponseBody
	@ExceptionHandler(value = FetchEmptyException.class)
	public ResponseEntity<?> handleException(final FetchEmptyException exception){
		E_LOGGER.error(exception.getMsg());
		return response.body(exception.getMsg());
	}
	
	@ResponseBody
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public ResponseEntity<?> handleException(final MissingServletRequestParameterException exception){
		final String name = exception.getParameterName();
		if(E_LOGGER.isErrorEnabled()) {
			E_LOGGER.error("ERROR : " + name + " parameter is missing.");
		}
		return response.body("ERROR : " + name + " parameter is missing.");
	}
}
