package com.board.exception.user;

public class UpdateUserException extends UserException {

	public UpdateUserException() {}

	public UpdateUserException(String message) {
		super(message);
	}

	public UpdateUserException(UserExceptionMessage userExceptionMessage) {
		super(userExceptionMessage);
	}

}
