package com.board.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.board.domain.ChatMessageVO;
import com.board.domain.ChatRoomVO;
import com.board.service.ChatService;
import com.board.util.LoginUserUtils;
import com.board.util.TextFileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

/**
 * WebSocket으로 들어오는 메시지 발행을 처리한다. 클라이언트에서 prefix를 붙여서 처리한다. 현재 설정값은 "/topic"이므로
 * 클라이언트에서 "/topic/message"로 발행 요청을 하면 Controller가 해당 값을 가진 MessageMapping 메소드를
 * 찾아서 처리한다.
 *
 */
@RestController
@RequiredArgsConstructor
@Log4j
public class ChatController {

	private final ChatService chatService;

	private final SimpMessagingTemplate simpMessagingTemplate;

	/*
	 * 비즈니스 로직 & 페이지 호출
	 */

	@PostMapping("/chat/room")
	public String chatroomAdd(@RequestBody ChatRoomVO params) {
		String roomId = UUID.randomUUID().toString().replace("-", "");
		if (chatService.isChatRoomExist(params)) {
			return "EXISTED";
		}
		chatService.addNewChat(params, roomId);
		return roomId;
	}

	@GetMapping("/chat/room")
	public ModelAndView newChatRoom(@RequestParam("roomId") String roomId, Model model) {
		String buyer = LoginUserUtils.getUserId();
		model.addAttribute("roomId", roomId);
		model.addAttribute("buyer", buyer);
		return new ModelAndView("chat/chatroom");
	}

	@GetMapping("/chat/room-exist")
	public ModelAndView existedChatRoom(@RequestParam("prdtId") String prdtId,@RequestParam("seller") String seller, Model model) {
		String buyer = LoginUserUtils.getUserId();
		String roomId = chatService.getRoomId(prdtId, seller);
		model.addAttribute("roomId", roomId);
		model.addAttribute("buyer", buyer);
		return new ModelAndView("chat/chatroom");
	}

	@GetMapping("/chat/list")
	public ModelAndView chatList() {
		ModelAndView mv = new ModelAndView("chat/list");
		mv.addObject("chatList", chatService.getChatList());
		return mv;
	}
	
	@PostMapping("/chat/delete")
	public void deleteChat(@RequestParam("roomId") String roomId) {
		chatService.deleteChat(roomId);
	}

	/*
	 * WebSocket
	 */

	@MessageMapping("/join")
	public void joinUser(@Payload ChatMessageVO message, SimpMessageHeaderAccessor headerAccessor) {
		System.err.println("joinUser");
		// 웹소켓 세션에 사용자 이름 등록
		System.err.println(message);
		headerAccessor.getSessionAttributes().put("username", message.getSender());
		simpMessagingTemplate.convertAndSend("/topic/join/" + message.getRoomId(), message);
	}

	@MessageMapping("/message")
	public void sendMessage(@Payload ChatMessageVO message) throws IOException {
		// chatService.saveMessage(message);
		TextFileUtils.saveMessages(message.getRoomId(), message.getContent());
		simpMessagingTemplate.convertAndSend("/topic/join/" + message.getRoomId(), message);
	}

}
