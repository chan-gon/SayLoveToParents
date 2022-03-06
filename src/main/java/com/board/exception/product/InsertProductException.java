package com.board.exception.product;

public class InsertProductException extends ProductException {

	public InsertProductException() {}

	public InsertProductException(ProductExceptionMessage productExceptionMessage) {
		super(productExceptionMessage);
	}

	public InsertProductException(String message) {
		super(message);
	}

}
