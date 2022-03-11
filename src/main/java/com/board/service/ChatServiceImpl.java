package com.board.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.ChatMessageDTO;
import com.board.domain.ChatRoomDTO;
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
	public void addNewChat(String prdtId) {
		String userId = LoginUserUtils.getUserId();
		String accountId = userMapper.getAccountId(userId);
		String checkRoomDup = chatMapper.isChatRoomExist(prdtId, accountId);
		if (checkRoomDup.equals("EXISTED")) {
			throw new RuntimeException();
		}
		ChatRoomDTO newRoom = ChatRoomDTO.builder()
				.roomId(UUID.randomUUID().toString())
				.accountId(accountId)
				.prdtId(prdtId)
				.build();
		chatMapper.addNewChat(newRoom);
	}

	@Override
	@Transactional
	public void sendMessage(ChatMessageDTO message) {
		String userId = LoginUserUtils.getUserId();
		String accountId = userMapper.getAccountId(userId);
		ChatMessageDTO sendMessage = ChatMessageDTO.builder()
				.roomId(message.getRoomId())
				.writer(accountId)
				.message(message.getMessage())
				.build();
		chatMapper.sendMessage(sendMessage);
	}

	/**
	 *	채팅방과 채팅방 메시지 두 개 삭제
	 */
	@Override
	public void deleteChat(String roomId) {
		chatMapper.deleteChat(roomId);
	}

	@Override
	public List<ChatRoomDTO> getChatList() {
		String userId = LoginUserUtils.getUserId();
		String accountId = userMapper.getAccountId(userId);
		List<ChatRoomDTO> list = new ArrayList<ChatRoomDTO>(chatMapper.getChatList(accountId));
		Collections.reverse(list);
		return list;
	}

	@Override
	public String getRoomId(String prdtId) {
		String userId = LoginUserUtils.getUserId();
		String accountId = userMapper.getAccountId(userId);
		return chatMapper.getRoomId(prdtId, accountId);
	}

	@Override
	public String isChatRoomExist(String prdtId) {
		String userId = LoginUserUtils.getUserId();
		String accountId = userMapper.getAccountId(userId);
		return chatMapper.isChatRoomExist(prdtId, accountId);
	}

}
