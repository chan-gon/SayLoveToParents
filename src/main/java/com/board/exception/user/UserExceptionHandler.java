package com.board.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.board.common.ApiResponse;
import com.board.controller.UserController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackageClasses = { UserController.class })
public class UserExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ApiResponse userExceptionHandler(UserException e) {
		log.warn("{}...", e.getClass(), e);

		return new ApiResponse(HttpStatus.OK, e.getUserExceptionMessage().getMessage());
	}
}
