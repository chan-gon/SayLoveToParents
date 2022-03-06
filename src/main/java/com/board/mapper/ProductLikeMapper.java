package com.board.mapper;

import com.board.domain.ProductLikeVO;

public interface ProductLikeMapper {
	
	ProductLikeVO isLikedOrNot(String prdtId);

}
