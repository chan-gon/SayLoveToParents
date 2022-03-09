package com.board.exception.user;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.board.controller.UserController;

import lombok.extern.slf4j.Slf4j;

/**
 * UserController에서 처리되는 모든 예외를 받아서 상세 내용을 출력한다.
 *
 */
@Slf4j
@RestControllerAdvice(basePackageClasses = { UserController.class })
public class UserExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ModelAndView userExceptionHandler(UserException e, Model model) {
		log.warn("===== START =====");
		log.warn("{}...", e.getClass(), e);
		log.warn("===== END =====");
		model.addAttribute("EXCEPTION_MSG", e.getUserExceptionMessage().getMessage());
		return new ModelAndView("error/exception");
	}
}
