package com.board.exception.product;

public class UpdateProductException extends ProductException {

	public UpdateProductException() {}

	public UpdateProductException(ProductExceptionMessage productExceptionMessage) {
		super(productExceptionMessage);
	}

	public UpdateProductException(String message) {
		super(message);
	}

}
