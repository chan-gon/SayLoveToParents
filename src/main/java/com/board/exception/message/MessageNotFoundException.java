package com.board.exception.message;

public class MessageNotFoundException extends MessageException {

	public MessageNotFoundException() {}

	public MessageNotFoundException(MessageExceptionMessange chattingExceptionMessange) {
		super(chattingExceptionMessange);
	}

	public MessageNotFoundException(String message) {
		super(message);
	}

}
