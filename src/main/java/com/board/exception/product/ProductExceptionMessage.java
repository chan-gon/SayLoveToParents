package com.board.exception.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductExceptionMessage {
	
	INSERT_FAIL ("상품 등록에 실패했습니다."),
    NOT_FOUND ("상품 조회에 실패했습니다."),
    UPDATE_FAIL ("상품 수정에 실패했습니다."),
    DELETE_FAIL ("상품 삭제에 실패했습니다."),
    ALREADY_EXISTS ("이미 존재하는 상품 입니다."),

	;

	private String message;
	
}
