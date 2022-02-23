package com.board.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ProductVO {
	
	private final int prdtNum;
	private final String userId;
	private final int storeNum;
	private final String prdtName;
	private final int prdtPrice;
	private final String prdtInfo;
	private final String prdtCondition;
	private final String prdtIsTradeable;
	private final Date prdtRegDate;
	private final Date prdtUpdateDate;
	private final String prdtIsDeliveryFree;
	private final int prdtLikeCnt;
	private final String prdtTradeLoc;
	private final String prdtTradeStatus;
	private final String prdtCategory;

}
