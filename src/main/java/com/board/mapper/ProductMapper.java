package com.board.mapper;

import java.util.List;

import com.board.domain.ProductVO;

public interface ProductMapper {

	ProductVO getProductById(String prdtId);

	List<ProductVO> getProductList();

	void addNewProduct(ProductVO product);

	void deleteProduct(ProductVO product);

	void likeProuct(String prdtId);
	
	void unlikeProuct(String prdtId);
}
