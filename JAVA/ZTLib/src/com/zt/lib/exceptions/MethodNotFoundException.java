package com.zt.lib.exceptions;

public class MethodNotFoundException extends Exception {

	private static final long serialVersionUID = 5530514058053230555L;

	public MethodNotFoundException()
	{
		super();
	}
	
	public MethodNotFoundException(String message)
	{
		super(message);
	}
	
}
