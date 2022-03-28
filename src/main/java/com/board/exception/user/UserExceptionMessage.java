package com.board.exception.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserExceptionMessage {

    NOT_FOUND ("사용자 조회에 실패했습니다."),
    ALREADY_EXISTS ("이미 존재하는 사용자 입니다."),

	;

	private String message;
	
}
