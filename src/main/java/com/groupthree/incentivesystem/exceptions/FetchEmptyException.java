package com.groupthree.incentivesystem.exceptions;

/**
 * Exception class for when fetch is null
 * @author Tejas
 *
 */
public class FetchEmptyException extends RuntimeException{
	private static final long serialVersionUID = 6188695445767049316L;
	private final String msg;
	
	public FetchEmptyException(final String msg){
		super();
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}
}
