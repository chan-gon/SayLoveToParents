package com.board.exception.message;

public class MessageInsertException extends MessageException {

	public MessageInsertException() {}

	public MessageInsertException(MessageExceptionMessange chattingExceptionMessange) {
		super(chattingExceptionMessange);
	}

	public MessageInsertException(String message) {
		super(message);
	}

}
