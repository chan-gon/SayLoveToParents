package com.board.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.board.controller.UserController;
import com.board.exception.common.ApiResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * UserController에서 처리되는 모든 예외를 받아서 상세 내용을 출력한다.
 *
 */
@Slf4j
@RestControllerAdvice(basePackageClasses = { UserController.class })
public class UserExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ApiResponse userExceptionHandler(UserException e) {
		log.warn("===== START =====");
		log.warn("{}...", e.getClass(), e);
		log.warn("===== END =====");
		return new ApiResponse(HttpStatus.OK, e.getUserExceptionMessage().getMessage());
	}
}
