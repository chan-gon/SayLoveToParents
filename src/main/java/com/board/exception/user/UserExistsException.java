package com.board.exception.user;

public class UserExistsException extends UserException {

	public UserExistsException() {}

	public UserExistsException(String message) {
		super(message);
	}

	public UserExistsException(UserExceptionMessage userExceptionMessage) {
		super(userExceptionMessage);
	}

}
