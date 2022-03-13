package com.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class ChatRoomVO {

	private final String roomId;
	private final String prdtId;
	private final String buyer;
	private final String seller;
	
	private final ProductVO productVO;

}
