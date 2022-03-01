package com.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.board.domain.ProductVO;

public interface ProductService {

	List<ProductVO> getProductById(String accountId);

	List<ProductVO> getProductList();

	void addNewProduct(String userId, ProductVO product, List<MultipartFile> productImage);
}
