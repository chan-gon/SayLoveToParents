package com.board.exception.message;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.board.controller.MessageController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackageClasses = { MessageController.class })
public class MessageExceptionHandler {

	@ExceptionHandler(MessageException.class)
	public ModelAndView chattingExceptionHandler(MessageException e, Model model) {
		log.warn("===== START =====");
		log.warn("{}...", e.getClass(), e);
		log.warn("===== END =====");
		model.addAttribute("EXCEPTION_MSG", e.getChattingExceptionMessange().getMessage());
		return new ModelAndView("error/exception");
	}

}
