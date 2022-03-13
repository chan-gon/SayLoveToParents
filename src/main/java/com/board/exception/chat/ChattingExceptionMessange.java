package com.board.exception.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChattingExceptionMessange {
	
	INSERT_FAIL ("채팅방 생성에 실패했습니다."),
    NOT_FOUND ("채팅방 조회에 실패했습니다."),
    DELETE_FAIL ("채팅방 삭제에 실패했습니다."),
    ALREADY_EXISTS ("이미 존재하는 채팅방 입니다."),

	;

	private String message;
}
