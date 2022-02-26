package com.board.service;

import org.springframework.stereotype.Service;

import com.board.domain.ProductVO;
import com.board.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductMapper mapper;

	@Override
	public void addNewProduct(ProductVO product) {
		
	}

}
