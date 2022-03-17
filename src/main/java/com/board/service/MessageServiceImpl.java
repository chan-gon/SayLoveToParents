package com.board.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.MessageVO;
import com.board.exception.chat.ChattingExceptionMessange;
import com.board.exception.chat.ChattingNotFoundException;
import com.board.exception.chat.DeleteChattingException;
import com.board.mapper.MessageMapper;
import com.board.util.LoginUserUtils;
import com.board.util.TextFileUtils;

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

	/**
	 *	채팅방과 채팅방 메시지 두 개 삭제
	 */
	@Override
	@Transactional
	public void deleteChat(String roomId) {
		try {
			messageMapper.deleteChat(roomId);
			TextFileUtils.deleteFile(roomId);
		} catch (RuntimeException e) {
			throw new DeleteChattingException(ChattingExceptionMessange.DELETE_FAIL);
		}
	}

	@Override
	public List<MessageVO> getChatList() {
		try {
			String buyer = LoginUserUtils.getUserId();
			List<MessageVO> list = new ArrayList<MessageVO>(messageMapper.getChatList(buyer));
			Collections.reverse(list);
			return list;
		} catch (RuntimeException e) {
			throw new ChattingNotFoundException(ChattingExceptionMessange.NOT_FOUND);
		}
	}

	@Override
	public String getRoomId(String prdtId, String seller) {
		try {
			String buyer = LoginUserUtils.getUserId();
			return messageMapper.getRoomId(prdtId, buyer, seller);
		} catch (RuntimeException e) {
			throw new ChattingNotFoundException(ChattingExceptionMessange.NOT_FOUND);
		}
	}

	@Override
	public boolean isChatRoomExist(MessageVO params) {
		try {
			String buyerId = LoginUserUtils.getUserId();
			String result = messageMapper.isChatRoomExist(params.getPrdtId(), buyerId, params.getSeller());
			if (result.equals("EXISTED")) {
				return true;
			}
			return false;
		} catch (RuntimeException e) {
			throw new ChattingNotFoundException(ChattingExceptionMessange.NOT_FOUND);
		}
	}

}
