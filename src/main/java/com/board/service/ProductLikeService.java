package com.board.service;

import com.board.domain.ProductLikeVO;

public interface ProductLikeService {
	
	ProductLikeVO isLikedOrNot(String prdtId);

}
