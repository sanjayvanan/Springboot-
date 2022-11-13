package com.web_crud.pjct1.Exception;
public class ProductNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	public ProductNotFoundException(String message)
	{
		super(message);
	}
}
