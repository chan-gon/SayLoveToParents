package com.board.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j;

@ControllerAdvice
@Log4j
public class ErrorController {

	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex) {
		log.info("Exception = " + ex.getMessage());
		return "error/errorPage";
	}
}
