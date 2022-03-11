package com.board.exception.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChattingException extends RuntimeException {

	private ChattingExceptionMessange chattingExceptionMessange;

	public ChattingException() {}

	public ChattingException(String message) {
		super(message);
	}

}
