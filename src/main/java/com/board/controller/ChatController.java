package com.board.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j;

/**
 * WebSocket으로 들어오는 메시지 발행을 처리한다.
 * 클라이언트에서 prefix를 붙여서 처리한다. 현재 설정값은 "/pub"이므로 "/pub/chat/enter"로 발행 요청을 하면
 * Controller가 해당 메시지를 받아서 처리한다.
 *
 */
@Controller
@Log4j
public class ChatController {
	
	@MessageMapping("/enter")
	@SendTo("/topic/enter")
	public String enter(String time) {
		time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		System.err.println(time);
		return time;
	}

	@MessageMapping("/message")
	@SendTo("/topic/message")
	public String message(String message) {
		System.err.println(message);
		return message;
	}

}
