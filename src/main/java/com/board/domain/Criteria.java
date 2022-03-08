package com.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 페이징 처리
 *
 */
@Getter
@Setter
@ToString
public class Criteria {
	private int pageNum;
	private int amount;

	/*
	 * 생성자를 통해 기본값을 1페이지에 10개로 처리
	 */
	public Criteria() {
		this(1, 10);
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

}
