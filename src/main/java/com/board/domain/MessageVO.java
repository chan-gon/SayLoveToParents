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
public class MessageVO {

	private final String prdtId;
	private final String buyer;
	private final String seller;
	private final String content;
	private final Date sendDate;
	private final String type;
	private final ProductVO productVO;

}
