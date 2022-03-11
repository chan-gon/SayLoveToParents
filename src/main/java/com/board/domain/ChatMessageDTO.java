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
public class ChatMessageDTO {

	private final String roomId;
	private final String writer;
	private final String message;
	private final Date sendDate;

}
