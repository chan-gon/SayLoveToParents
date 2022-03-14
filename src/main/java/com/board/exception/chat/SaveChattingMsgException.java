package com.board.exception.chat;

public class SaveChattingMsgException extends ChattingException {

	public SaveChattingMsgException() {}

	public SaveChattingMsgException(ChattingExceptionMessange chattingExceptionMessange) {
		super(chattingExceptionMessange);
	}

	public SaveChattingMsgException(String message) {
		super(message);
	}

}
