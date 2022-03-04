package com.board.exception.user;

public class InsertUserException extends UserException {

	public InsertUserException() {}

	public InsertUserException(String message) {
		super(message);
	}

	public InsertUserException(UserExceptionMessage userExceptionMessage) {
		super(userExceptionMessage);
	}

}
