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
public class ProductVO {
	
	private final String prdtId;
	private final String accountId;
	private final String prdtName;
	private final int prdtPrice;
	private final String prdtCategory;
	private final String prdtInfo;
	private final String prdtCondition;
	private final String prdtIsTradeable;
	private final Date prdtRegDate;
	private final Date prdtUpdateDate;
	private final String prdtIsDeliveryFree;
	private final int prdtLikeCnt;
	private final String prdtTradeLoc;
	private final String prdtTradeStatus;

}
