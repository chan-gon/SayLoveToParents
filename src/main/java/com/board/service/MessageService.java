package com.board.service;

import java.util.List;

import com.board.domain.MessageVO;

public interface MessageService {

	void sendMessage(MessageVO message);
	
	void sendResponse(MessageVO message);

	List<MessageVO> getReceivedMsg(String seller);

	List<MessageVO> getSentMsg(String buyer);

	List<MessageVO> getReceivedMsgList(String seller, String buyer);

	List<MessageVO> getSentMsgList(String buyer, String seller);

}
