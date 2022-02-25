package com.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class StoreVO {
	private final int storeId;
	private final String userId;
	private final int storeReviewId;
	private final String storeIntro;
}
