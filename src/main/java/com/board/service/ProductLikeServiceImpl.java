package com.board.service;

import org.springframework.stereotype.Service;

import com.board.domain.ProductLikeVO;
import com.board.exception.product.ProductExceptionMessage;
import com.board.exception.product.ProductNotFoundException;
import com.board.mapper.ProductLikeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductLikeServiceImpl implements ProductLikeService {

	private final ProductLikeMapper productLikeMapper;

	/**
	 *	현재 로그인한 사용자가 해당 상품을 찜했는지 안했는지 여부 확인
	 */
	@Override
	public ProductLikeVO isLikedOrNot(String prdtId) {
		try {
			return productLikeMapper.isLikedOrNot(prdtId);
		} catch (RuntimeException e) {
			throw new ProductNotFoundException(ProductExceptionMessage.NOT_FOUND);
		}
	}

}
