package com.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.ProductLikeVO;
import com.board.mapper.ProductLikeMapper;
import com.board.mapper.UserMapper;
import com.board.util.LoginUserUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductLikeServiceImpl implements ProductLikeService {

	private final ProductLikeMapper productLikeMapper;
	
	private final UserMapper userMapper;

	/**
	 *	현재 로그인한 사용자가 해당 상품을 찜했는지 안했는지 여부 확인
	 */
	@Override
	@Transactional
	public ProductLikeVO isLikedOrNot(String prdtId) {
		String username = LoginUserUtils.getUserId();
		String accountId = userMapper.getAccountId(username);
		ProductLikeVO like = ProductLikeVO.builder()
				.prdtId(prdtId)
				.accountId(accountId)
				.build();
		return productLikeMapper.isLikedOrNot(like);
	}

}
