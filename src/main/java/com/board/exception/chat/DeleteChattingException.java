package com.board.exception.chat;

public class DeleteChattingException extends ChattingException {

	public DeleteChattingException() {}

	public DeleteChattingException(ChattingExceptionMessange chattingExceptionMessange) {
		super(chattingExceptionMessange);
	}

	public DeleteChattingException(String message) {
		super(message);
	}

}
