package com.board.service;

import java.util.List;

import com.board.domain.ChatMessageDTO;
import com.board.domain.ChatRoomDTO;

public interface ChatService {

	void addNewChat(String prdtId);

	void sendMessage(ChatMessageDTO message);

	void deleteChat(String roomId);

	List<ChatRoomDTO> getChatList();
	
	String getRoomId(String prdtId);
	
	String isChatRoomExist(String prdtId);
	
}
