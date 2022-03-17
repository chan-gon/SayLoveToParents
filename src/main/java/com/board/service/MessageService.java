package com.board.service;

import java.util.List;

import com.board.domain.MessageVO;

public interface MessageService {

	void sendMessage(MessageVO message);

	void deleteChat(String roomId);

	List<MessageVO> getChatList();
	
	String getRoomId(String prdtId, String seller);
	
	boolean isChatRoomExist(MessageVO params);
	
}
