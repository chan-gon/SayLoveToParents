package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.board.domain.ChatMessageDTO;
import com.board.domain.ChatRoomDTO;

public interface ChatMapper {

	void addNewChat(ChatRoomDTO room);

	void sendMessage(ChatMessageDTO message);

	void deleteChat(String roomId);

	List<ChatRoomDTO> getChatList(String accountId);

	String getRoomId(@Param("prdtId") String prdtId, @Param("accountId") String accountId);

	String isChatRoomExist(@Param("prdtId") String prdtId, @Param("accountId") String accountId);

}
