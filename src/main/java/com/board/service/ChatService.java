package com.board.service;

import java.util.List;

import com.board.domain.ChatMessageVO;
import com.board.domain.ChatRoomVO;

public interface ChatService {

	void addNewChat(String roomId, String prdtId, String userId);

	void saveMessage(ChatMessageVO message);

	void deleteChat(String roomId);

	List<ChatRoomVO> getChatList();
	
	String getRoomId(String prdtId);
	
	String isChatRoomExist(String prdtId);
	
}
