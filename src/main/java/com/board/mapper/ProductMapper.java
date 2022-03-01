package com.board.mapper;

import java.util.List;

import com.board.domain.ProductVO;

public interface ProductMapper {

	List<ProductVO> getProductById(String accountId);

	List<ProductVO> getProductList();

	void addNewProduct(ProductVO product);

	void deleteProduct(ProductVO product);

}
