package com.board.exception.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {
	
	private UserExceptionMessage userExceptionMessage;

	public UserException() {}

	public UserException(String message) {
		super(message);
	}
	
}
