package com.board.exception.product;

public class ProductNotFoundException extends ProductException {

	public ProductNotFoundException() {}

	public ProductNotFoundException(ProductExceptionMessage productExceptionMessage) {
		super(productExceptionMessage);
	}

	public ProductNotFoundException(String message) {
		super(message);
	}

}
