package com.board.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.board.domain.ChatMessageVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

/**
 * WebSocket으로 들어오는 메시지 발행을 처리한다. 클라이언트에서 prefix를 붙여서 처리한다. 현재 설정값은 "/pub"이므로
 * 클라이언트에서 "/pub/message"로 발행 요청을 하면 Controller가 해당 값을 가진 MessageMapping 메소드를
 * 찾아서 처리한다.
 *
 */
@Controller
@RequiredArgsConstructor
@Log4j
public class ChatController {
	
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/chat/join")
	public void joinUser(@Payload ChatMessageVO message, SimpMessageHeaderAccessor headerAccessor) {
		System.err.println("joinUser");
		// 웹소켓 세션에 사용자 이름 등록
		headerAccessor.getSessionAttributes().put("username", message.getSender());
		simpMessagingTemplate.convertAndSend("/topic/join/"+message.getRoomId(), message);
	}
	
	@MessageMapping("/chat/message")
	public void sendMessage(@Payload ChatMessageVO message) {
		//chatService.saveMessage(message);
		simpMessagingTemplate.convertAndSend("/topic/join/"+message.getRoomId(), message);
	}


}
