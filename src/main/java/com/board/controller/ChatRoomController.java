package com.board.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.board.domain.ChatMessageDTO;
import com.board.service.ChatService;
import com.board.util.LoginUserUtils;

import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {

	private final ChatService chatService;

	/*
	 * 비즈니스 로직
	 */

	@PostMapping("/room")
	public void create(@RequestParam("prdtId") String prdtId) {
		chatService.addNewChat(prdtId);
	}

	@PostMapping("/message")
	public void sendMessage(@RequestBody ChatMessageDTO message) {
		chatService.sendMessage(message);
	}
	
	@GetMapping("/room-check")
	public String roomCheck(@RequestParam("prdtId") String prdtId) {
		return chatService.isChatRoomExist(prdtId);
	}

	/*
	 * 페이지 호출
	 */

	@GetMapping("/room")
	public ModelAndView chatroom(@RequestParam("prdtId") String prdtId, Model model) {
		String userId = LoginUserUtils.getUserId();
		String roomId = chatService.getRoomId(prdtId);
		model.addAttribute("roomId", roomId);
		model.addAttribute("userId", userId);
		return new ModelAndView("chat/chatroom");
	}

	@GetMapping("/list")
	public ModelAndView chatList() {
		ModelAndView mv = new ModelAndView("chat/chat-list");
		mv.addObject("list", chatService.getChatList());
		return mv;
	}

}
