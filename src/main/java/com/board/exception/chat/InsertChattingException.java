package com.board.exception.chat;

public class InsertChattingException extends ChattingException {

	public InsertChattingException() {}

	public InsertChattingException(ChattingExceptionMessange chattingExceptionMessange) {
		super(chattingExceptionMessange);
	}

	public InsertChattingException(String message) {
		super(message);
	}

}
