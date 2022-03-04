package com.board.exception.user;

public class DeleteUserException extends UserException {

	public DeleteUserException() {}

	public DeleteUserException(String message) {
		super(message);
	}

	public DeleteUserException(UserExceptionMessage userExceptionMessage) {
		super(userExceptionMessage);
	}
	
}
