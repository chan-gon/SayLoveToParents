package com.board.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageExceptionMessange {
	
	INSERT_FAIL ("메시지 전송에 실패했습니다."),
    NOT_FOUND ("메시지 조회에 실패했습니다."),

	;

	private String message;
}
