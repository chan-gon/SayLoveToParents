package com.board.exception.common;

import java.io.IOException;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

/**
 * @RestControllerAdvice
 * : @ControllerAdvice + @ResponseBody
 * 애플리케이션 내부에서 발생하는 에러를 제어하는 컨트롤러.
 * 대상을 지정하지 않으면 모든 컨트롤러에 적용된다.
 * 
 * 서비스 전역에서 발생하는 에러를 관리한다.
 *
 */
@RestControllerAdvice
@Log4j
public class CommonExceptionHandler {

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView NoHandlerFound(NoHandlerFoundException ex) {
		log.info("Exception-NoHandlerFound = " + ex.getMessage());
		return new ModelAndView("error/404");
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ModelAndView MethodNotSupported(HttpRequestMethodNotSupportedException ex) {
		log.info("Exception-MethodNotSupported = " + ex.getMessage());
		return new ModelAndView("error/404");
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ModelAndView NullPointer(NullPointerException ex) {
		log.info("Exception-Null = " + ex.getMessage());
		return new ModelAndView("error/500");
	}
	
	@ExceptionHandler(IOException.class)
	public ModelAndView NullPointer(IOException ex) {
		log.info("IO-Exception = " + ex.getMessage());
		return new ModelAndView("error/500");
	}
	
}
