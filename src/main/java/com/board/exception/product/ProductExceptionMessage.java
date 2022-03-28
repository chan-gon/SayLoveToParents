package com.board.exception.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductExceptionMessage {
	
    NOT_FOUND ("상품 조회에 실패했습니다."),

	;

	private String message;
	
}
