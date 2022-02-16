package com.board.exception;

import org.apache.commons.mail.EmailException;

public class EmailCannotSendException extends EmailException {

	public EmailCannotSendException() {
		super();
	}

	public EmailCannotSendException(String msg) {
		super(msg);
	}
	
	

}
