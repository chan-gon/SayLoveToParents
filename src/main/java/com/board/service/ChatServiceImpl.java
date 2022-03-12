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
import com.board.exception.chat.InsertChattingException;
import com.board.mapper.ChatMapper;
import com.board.mapper.UserMapper;
import com.board.util.LoginUserUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatMapper chatMapper;
	
	private final UserMapper userMapper;

	@Override
	@Transactional
	public void addNewChat(String roomId, String prdtId, String userId) {
			String accountId = userMapper.getAccountId(userId);
			System.err.println("accountId = " + accountId);
			String checkRoomDup = chatMapper.isChatRoomExist(prdtId, accountId);
			if (checkRoomDup.equals("EXISTED")) {
				throw new RuntimeException();
			}
			ChatRoomVO newRoom = ChatRoomVO.builder()
					.roomId(roomId)
					.accountId(accountId)
					.prdtId(prdtId)
					.build();
			chatMapper.addNewChat(newRoom);
	}

	@Override
	@Transactional
	public void saveMessage(ChatMessageVO message) {
		String userId = LoginUserUtils.getUserId();
		String accountId = userMapper.getAccountId(userId);
		ChatMessageVO sendMessage = ChatMessageVO.builder()
				.roomId(message.getRoomId())
				.sender(accountId)
				.content(message.getContent())
				.build();
		chatMapper.saveMessage(sendMessage);
	}

	/**
	 *	채팅방과 채팅방 메시지 두 개 삭제
	 */
	@Override
	public void deleteChat(String roomId) {
		chatMapper.deleteChat(roomId);
	}

	@Override
	public List<ChatRoomVO> getChatList() {
		String userId = LoginUserUtils.getUserId();
		String accountId = userMapper.getAccountId(userId);
		List<ChatRoomVO> list = new ArrayList<ChatRoomVO>(chatMapper.getChatList(accountId));
		Collections.reverse(list);
		return list;
	}

	@Override
	public String getRoomId(String prdtId) {
		try {
			String userId = LoginUserUtils.getUserId();
			String accountId = userMapper.getAccountId(userId);
			return chatMapper.getRoomId(prdtId, accountId);
		} catch (RuntimeException e) {
			throw new ChattingNotFoundException(ChattingExceptionMessange.NOT_FOUND);
		}
	}

	@Override
	public String isChatRoomExist(String prdtId) {
		String userId = LoginUserUtils.getUserId();
		String accountId = userMapper.getAccountId(userId);
		return chatMapper.isChatRoomExist(prdtId, accountId);
	}

}
