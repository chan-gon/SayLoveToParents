package com.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.MessageVO;
import com.board.exception.message.MessageExceptionMessange;
import com.board.exception.message.MessageInsertException;
import com.board.exception.message.MessageNotFoundException;
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
		try {
			String loginUser = LoginUserUtils.getUserId();
			MessageVO newMessage = MessageVO.builder()
					.prdtId(message.getPrdtId())
					.buyer(loginUser)
					.seller(message.getSeller())
					.content(message.getContent())
					.build();
			messageMapper.sendMessage(newMessage);
		} catch (RuntimeException e) {
			throw new MessageInsertException(MessageExceptionMessange.INSERT_FAIL);
		}
	}

	@Override
	public List<MessageVO> getReceivedMsg(String seller) {
		try {
			return messageMapper.getReceivedMsg(seller);
		} catch (RuntimeException e) {
			throw new MessageNotFoundException(MessageExceptionMessange.NOT_FOUND);
		}
	}

	@Override
	public List<MessageVO> getSentMsg(String buyer) {
		try {
			return messageMapper.getSentMsg(buyer);
		} catch (RuntimeException e) {
			throw new MessageNotFoundException(MessageExceptionMessange.NOT_FOUND);
		}
	}

	@Override
	public List<MessageVO> getReceivedMsgList(String seller, String buyer) {
		try {
			return messageMapper.getReceivedMsgList(seller, buyer);
		} catch (RuntimeException e) {
			throw new MessageNotFoundException(MessageExceptionMessange.NOT_FOUND);
		}
	}

	@Override
	public List<MessageVO> getSentMsgList(String buyer, String seller) {
		try {
			return messageMapper.getSentMsgList(buyer, seller);
		} catch (RuntimeException e) {
			throw new MessageNotFoundException(MessageExceptionMessange.NOT_FOUND);
		}
	}

	@Override
	public void sendResponse(MessageVO message) {
		try {
			messageMapper.sendResponse(message);
		} catch (RuntimeException e) {
			throw new MessageNotFoundException(MessageExceptionMessange.NOT_FOUND);
		}
	}

}
