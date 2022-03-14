package com.board.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.ToString;

/**
 * 페이징 처리를 위한 클래스
 * Criteria(현재 페이지 번호, 페이지당 출력되는 데이터 수)와 전체 데이터 수(total)를 파라미터로 가지는 생성자 생성
 *
 */
@Getter
@ToString
public class PageDTO implements Serializable {

	private int startPage;
	private int endPage;
	private boolean prev, next;

	private int total;
	private Criteria cri;

	public PageDTO(Criteria cri, int total) {
		this.total = total;
		this.cri = cri;

		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
		this.startPage = this.endPage - 9;
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));

		if (realEnd < this.endPage) {
			this.endPage = realEnd;
		}

		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}

}
