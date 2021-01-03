package com.groupthree.incentivesystem.exceptions;

public class FetchEmptyException extends RuntimeException{
	private static final long serialVersionUID = 6188695445767049316L;
	private String msg;
	
	public FetchEmptyException(String msg){
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}
}
