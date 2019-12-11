package com.solace.springbootnetty.excep;

public class BusinessException extends RuntimeException{
	
	private static final long serialVersionUID = -1629974785937212496L;

	public BusinessException(String msg) {
		super(msg);
	}
	
}