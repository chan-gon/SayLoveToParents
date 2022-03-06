package com.board.exception.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserExceptionMessage {

	INSERT_FAIL ("사용자 등록에 실패했습니다."),
    NOT_FOUND ("사용자 조회에 실패했습니다."),
    UPDATE_FAIL ("사용자 수정에 실패했습니다."),
    DELETE_FAIL ("사용자 삭제에 실패했습니다."),
    PWD_UPDATE_FAIL ("비밀번호 변경에 실패했습니다."),
    ALREADY_EXISTS ("이미 존재하는 사용자 입니다."),

	;

	private String message;
	
}
