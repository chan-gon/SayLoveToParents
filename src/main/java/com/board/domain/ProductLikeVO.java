package com.board.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class ProductLikeVO implements Serializable {
	
	private final String prdtId;
	private final String accountId;
	private final Date createDate;
}
