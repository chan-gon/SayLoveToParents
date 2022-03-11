package com.board.exception.chat;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.board.controller.ChatRoomController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackageClasses = { ChatRoomController.class })
public class ChattingExceptionHandler {

	@ExceptionHandler(ChattingException.class)
	public ModelAndView chattingExceptionHandler(ChattingException e, Model model) {
		log.warn("===== START =====");
		log.warn("{}...", e.getClass(), e);
		log.warn("===== END =====");
		model.addAttribute("EXCEPTION_MSG", e.getChattingExceptionMessange().getMessage());
		return new ModelAndView("error/exception");
	}

}
