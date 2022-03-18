package com.board.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageException extends RuntimeException {

	private MessageExceptionMessange chattingExceptionMessange;

	public MessageException() {}

	public MessageException(String message) {
		super(message);
	}

}
