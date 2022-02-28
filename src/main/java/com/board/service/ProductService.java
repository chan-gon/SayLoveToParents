package com.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.board.domain.ProductVO;

public interface ProductService {

	void addNewProduct(String userId, ProductVO product, List<MultipartFile> productImage);
	
	List<ProductVO> getProductById(String accountId);
	
}
