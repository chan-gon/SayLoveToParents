package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.board.domain.ChatMessageVO;
import com.board.domain.ChatRoomVO;

public interface ChatMapper {

	void addNewChat(ChatRoomVO room);

	void saveMessage(ChatMessageVO message);

	void deleteChat(String roomId);

	List<ChatRoomVO> getChatList(String accountId);

	String getRoomId(@Param("prdtId") String prdtId, @Param("accountId") String accountId);

	String isChatRoomExist(@Param("prdtId") String prdtId, @Param("accountId") String accountId);

}
