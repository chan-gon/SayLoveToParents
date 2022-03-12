package com.board.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class ChatMessageVO {

	private final String roomId;
	private final String sender;
	private final String content;
	private final Date sendDate;
	private final MessageType type;

}
