package com.board.mapper;

import java.util.List;

import com.board.domain.ProductVO;

public interface ProductMapper {
	
	void addNewProduct(ProductVO product);
	
	List<ProductVO> getProductById(String accountId);
	
	void deleteProduct(ProductVO product);

}
