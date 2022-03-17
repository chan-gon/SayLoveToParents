package com.board.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.board.domain.MessageVO;
import com.board.service.MessageService;
import com.board.util.LoginUserUtils;

import lombok.RequiredArgsConstructor;

/**
 * WebSocket으로 들어오는 메시지 발행을 처리한다. 클라이언트에서 prefix를 붙여서 처리한다. 현재 설정값은 "/topic"이므로
 * 클라이언트에서 "/topic/message"로 발행 요청을 하면 Controller가 해당 값을 가진 MessageMapping 메소드를
 * 찾아서 처리한다.
 *
 */
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;

	/*
	 * 비즈니스 로직 & 페이지 호출
	 */

	@PostMapping
	public void sendMessage(@RequestBody MessageVO message) {
		messageService.sendMessage(message);
	}

	@GetMapping("/chat/room")
	public ModelAndView newChatRoom(@RequestParam("roomId") String roomId, Model model) {
		String buyer = LoginUserUtils.getUserId();
		model.addAttribute("roomId", roomId);
		model.addAttribute("buyer", buyer);
		//return new ModelAndView("chat/chatroom");
		return new ModelAndView("chat/chattemp");
	}

	@GetMapping("/chat/room-exist")
	public ModelAndView existedChatRoom(@RequestParam("prdtId") String prdtId,@RequestParam("seller") String seller, Model model) {
		String buyer = LoginUserUtils.getUserId();
		String roomId = messageService.getRoomId(prdtId, seller);
		model.addAttribute("roomId", roomId);
		model.addAttribute("buyer", buyer);
		return new ModelAndView("chat/chatroom");
	}

	@GetMapping("/chat/list")
	public ModelAndView chatList() {
		ModelAndView mv = new ModelAndView("chat/list");
		mv.addObject("chatList", messageService.getChatList());
		return mv;
	}
	
	@PostMapping("/chat/delete")
	public void deleteChat(@RequestParam("roomId") String roomId) {
		messageService.deleteChat(roomId);
	}

	/*
	 * WebSocket
	 */
	
	@GetMapping("/chat/room/{roomId}")
	public String roomInfo(@PathVariable("roomId") String roomId) {
		System.err.println("subscribe");
		return roomId;
	}

}
