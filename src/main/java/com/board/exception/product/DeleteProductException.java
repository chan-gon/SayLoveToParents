package com.board.exception.product;

public class DeleteProductException extends ProductException {

	public DeleteProductException() {}

	public DeleteProductException(ProductExceptionMessage productExceptionMessage) {
		super(productExceptionMessage);
	}

	public DeleteProductException(String message) {
		super(message);
	}

}
