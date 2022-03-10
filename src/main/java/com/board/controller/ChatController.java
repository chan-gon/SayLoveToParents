package com.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.board.util.LoginUserUtils;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class ChatController {

	@GetMapping("/chat")
	public String chat(Model model) {

		// 로그인한 사용자 아이디 가져오기
		String loggedInUserId = LoginUserUtils.getUserId();
		log.info("=====================================");
		log.info("@ChatController, GET chat / Username : " + loggedInUserId);
		model.addAttribute("userid", loggedInUserId);
		return "chat/chat";
		
	}

}
