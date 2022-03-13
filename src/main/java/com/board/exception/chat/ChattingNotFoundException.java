package com.board.exception.chat;

public class ChattingNotFoundException extends ChattingException {

	public ChattingNotFoundException() {}

	public ChattingNotFoundException(ChattingExceptionMessange chattingExceptionMessange) {
		super(chattingExceptionMessange);
	}

	public ChattingNotFoundException(String message) {
		super(message);
	}

}
