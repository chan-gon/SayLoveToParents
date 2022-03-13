package com.board.service;

import java.util.List;

import com.board.domain.ChatMessageVO;
import com.board.domain.ChatRoomVO;

public interface ChatService {

	void addNewChat(ChatRoomVO params, String roomId);

	void saveMessage(ChatMessageVO params);

	void deleteChat(String roomId);

	List<ChatRoomVO> getChatList();
	
	String getRoomId(String prdtId, String seller);
	
	boolean isChatRoomExist(ChatRoomVO params);
	
}
