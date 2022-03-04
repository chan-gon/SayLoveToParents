package com.board.exception.user;

public class UserNotFoundException extends UserException {

	public UserNotFoundException() {}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(UserExceptionMessage userExceptionMessage) {
		super(userExceptionMessage);
	}

}
