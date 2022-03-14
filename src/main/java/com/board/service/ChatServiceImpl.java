package com.board.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.ChatMessageVO;
import com.board.domain.ChatRoomVO;
import com.board.exception.chat.ChattingExceptionMessange;
import com.board.exception.chat.ChattingNotFoundException;
import com.board.exception.chat.DeleteChattingException;
import com.board.exception.chat.InsertChattingException;
import com.board.exception.chat.SaveChattingMsgException;
import com.board.mapper.ChatMapper;
import com.board.util.LoginUserUtils;
import com.board.util.TextFileUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatMapper chatMapper;

	@Override
	@Transactional
	public void addNewChat(ChatRoomVO value, String roomId) {
		try {
			String buyerId = LoginUserUtils.getUserId();
			ChatRoomVO newRoom = ChatRoomVO.builder()
					.roomId(roomId)
					.buyer(buyerId)
					.seller(value.getSeller())
					.prdtId(value.getPrdtId())
					.build();
			chatMapper.addNewChat(newRoom);
		} catch (RuntimeException e) {
			throw new InsertChattingException(ChattingExceptionMessange.INSERT_FAIL);
		}
	}

	@Override
	@Transactional
	public void saveMessage(ChatMessageVO message) {
		try {
			ChatMessageVO sendMessage = ChatMessageVO.builder()
					.roomId(message.getRoomId())
					.sender(message.getSender())
					.content(message.getContent())
					.build();
			chatMapper.saveMessage(sendMessage);
		} catch (RuntimeException e) {
			throw new SaveChattingMsgException(ChattingExceptionMessange.SAVE_FAIL);
		}
	}

	/**
	 *	채팅방과 채팅방 메시지 두 개 삭제
	 */
	@Override
	@Transactional
	public void deleteChat(String roomId) {
		try {
			chatMapper.deleteChat(roomId);
			TextFileUtils.deleteFile(roomId);
		} catch (RuntimeException e) {
			throw new DeleteChattingException(ChattingExceptionMessange.DELETE_FAIL);
		}
	}

	@Override
	public List<ChatRoomVO> getChatList() {
		try {
			String buyer = LoginUserUtils.getUserId();
			List<ChatRoomVO> list = new ArrayList<ChatRoomVO>(chatMapper.getChatList(buyer));
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
			return chatMapper.getRoomId(prdtId, buyer, seller);
		} catch (RuntimeException e) {
			throw new ChattingNotFoundException(ChattingExceptionMessange.NOT_FOUND);
		}
	}

	@Override
	public boolean isChatRoomExist(ChatRoomVO params) {
		try {
			String buyerId = LoginUserUtils.getUserId();
			String result = chatMapper.isChatRoomExist(params.getPrdtId(), buyerId, params.getSeller());
			if (result.equals("EXISTED")) {
				return true;
			}
			return false;
		} catch (RuntimeException e) {
			throw new ChattingNotFoundException(ChattingExceptionMessange.NOT_FOUND);
		}
	}

}
