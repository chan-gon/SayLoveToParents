package com.board.controller;

import java.util.UUID;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.board.domain.ChatMessageVO;
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

//	@PostMapping("/room")
//	public void create(@RequestParam("prdtId") String prdtId) {
//		chatService.addNewChat(prdtId);
//	}
//
//	@PostMapping("/message")
//	public void sendMessage(@RequestBody ChatMessageVO message) {
//		chatService.saveMessage(message);
//	}
	
	@GetMapping("/room-check")
	public String roomCheck(@RequestParam("prdtId") String prdtId) {
		return chatService.isChatRoomExist(prdtId);
	}

	/*
	 * 페이지 호출
	 */

	@GetMapping("/room")
	public ModelAndView chatroom(@RequestParam("userName") String userName, @RequestParam("prdtId") String prdtId, Model model) {
		String userId = LoginUserUtils.getUserId();
		String roomId = UUID.randomUUID().toString().replace("-", "");
		model.addAttribute("userId", userId);
		model.addAttribute("roomId", roomId);
		model.addAttribute("prdtId", prdtId);
		model.addAttribute("userName", userName);
		return new ModelAndView("chat/chatroom");
	}

	@GetMapping("/list")
	public ModelAndView chatList() {
		ModelAndView mv = new ModelAndView("chat/chat-list");
		mv.addObject("list", chatService.getChatList());
		return mv;
	}
	
	@GetMapping("/test")
	public ModelAndView gotest() {
		return new ModelAndView("chat/test");
	}

}
