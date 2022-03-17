package com.board.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;

	/*
	 * 요청 처리
	 */
	@PostMapping
	public void sendMessage(@RequestBody MessageVO message) {
		messageService.sendMessage(message);
	}
	
	@PostMapping("/response")
	public void sendResponse(@RequestBody MessageVO message) {
		messageService.sendResponse(message);
	}

	/*
	 * 페이지 이동
	 */
	@GetMapping("/list")
	public ModelAndView messageList(Model model) {
		String currentUser = LoginUserUtils.getUserId();
		List<MessageVO> sentMessages = messageService.getSentMsg(currentUser);
		List<MessageVO> receivedMessages = messageService.getReceivedMsg(currentUser);
		model.addAttribute("sentMessages", sentMessages);
		model.addAttribute("receivedMessages", receivedMessages);
		return new ModelAndView("message/list");
	}

	@GetMapping("/received")
	public ModelAndView receivedMsg(@RequestParam("seller") String seller, @RequestParam("buyer") String buyer
			, @RequestParam("prdtId") String prdtId, @RequestParam("prdtName") String prdtName, Model model) {
		List<MessageVO> receivedMessages = messageService.getReceivedMsgList(seller, buyer);
		model.addAttribute("receivedMessages", receivedMessages);
		model.addAttribute("prdtId", prdtId);
		model.addAttribute("prdtName", prdtName);
		model.addAttribute("buyer", buyer);
		model.addAttribute("seller", seller);
		return new ModelAndView("message/receivedMsg");
	}

	@GetMapping("/sent")
	public ModelAndView sentMsg(@RequestParam("buyer") String buyer, @RequestParam("seller") String seller
			, @RequestParam("prdtId") String prdtId, @RequestParam("prdtName") String prdtName, Model model) {
		List<MessageVO> sentMessages = messageService.getSentMsgList(buyer, seller);
		model.addAttribute("sentMessages", sentMessages);
		model.addAttribute("prdtId", prdtId);
		model.addAttribute("prdtName", prdtName);
		model.addAttribute("buyer", buyer);
		model.addAttribute("seller", seller);
		return new ModelAndView("message/sentMsg");
	}
}
