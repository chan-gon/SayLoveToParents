package com.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.MessageVO;
import com.board.mapper.MessageMapper;
import com.board.util.LoginUserUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageMapper messageMapper;

	@Override
	@Transactional
	public void sendMessage(MessageVO message) {
		String loginUser = LoginUserUtils.getUserId();
		MessageVO newMessage = MessageVO.builder()
				.prdtId(message.getPrdtId())
				.buyer(loginUser)
				.seller(message.getSeller())
				.content(message.getContent())
				.build();
		messageMapper.sendMessage(newMessage);
	}

	@Override
	public List<MessageVO> getReceivedMsg(String seller) {
		return messageMapper.getReceivedMsg(seller);
	}

	@Override
	public List<MessageVO> getSentMsg(String buyer) {
		return messageMapper.getSentMsg(buyer);
	}

	@Override
	public List<MessageVO> getReceivedMsgList(String seller, String buyer) {
		return messageMapper.getReceivedMsgList(seller, buyer);
	}

	@Override
	public List<MessageVO> getSentMsgList(String buyer, String seller) {
		return messageMapper.getSentMsgList(buyer, seller);
	}

	@Override
	public void sendResponse(MessageVO message) {
		messageMapper.sendResponse(message);
	}

}
