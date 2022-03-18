package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.board.domain.MessageVO;

public interface MessageMapper {

	void sendMessage(MessageVO message);
	
	void sendResponse(MessageVO message);

	List<MessageVO> getReceivedMsg(String seller);

	List<MessageVO> getSentMsg(String buyer);

	List<MessageVO> getReceivedMsgList(@Param("seller") String seller, @Param("buyer") String buyer);

	List<MessageVO> getSentMsgList(@Param("buyer") String buyer, @Param("seller") String seller);
	
	/*
	 * 회원 탈퇴 관련
	 */
	void deleteMessagesPermanent(String userId);

}
