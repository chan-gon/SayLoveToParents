package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.board.domain.ChatMessageVO;
import com.board.domain.ChatRoomVO;

public interface ChatMapper {

	void addNewChat(ChatRoomVO params);

	void saveMessage(ChatMessageVO message);

	void deleteChat(String roomId);

	List<ChatRoomVO> getChatList(String buyer);

	String getRoomId(@Param("prdtId") String prdtId, @Param("buyer") String buyer, @Param("seller") String seller);

	String isChatRoomExist(@Param("prdtId") String prdtId, @Param("buyer") String buyer, @Param("seller") String seller);

}
