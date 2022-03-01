package com.board.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

/**
 * @RestControllerAdvice
 * : @ControllerAdvice + @ResponseBody
 * 대상을 지정하지 않으면 모든 컨트롤러에 적용된다.
 *
 */
@RestControllerAdvice
@Log4j
public class ErrorController {

	@ExceptionHandler(Exception.class)
	public ModelAndView handle500(Exception ex) {
		log.info("Exception = " + ex.getMessage());
		return new ModelAndView("error/error500");
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handle404(NoHandlerFoundException ex) {
		log.info("Exception = " + ex.getMessage());
		return new ModelAndView("error/error404");
	}
	
}
