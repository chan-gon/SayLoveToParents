package com.board.exception.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductException extends RuntimeException {
	
	private ProductExceptionMessage productExceptionMessage;

	public ProductException() {}

	public ProductException(String message) {
		super(message);
	}

}
