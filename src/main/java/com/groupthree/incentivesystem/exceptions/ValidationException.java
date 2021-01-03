package com.groupthree.incentivesystem.exceptions;

public class ValidationException extends RuntimeException{
	private static final long serialVersionUID = -1325082196937160165L;
	private String msg;
	
	public ValidationException(String msg){
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}
}
