package com.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.board.domain.Criteria;
import com.board.domain.ProductVO;

public interface ProductService {

	ProductVO getProductById(String prdtId);

	List<ProductVO> getProductList(Criteria cri);

	List<ProductVO> getProductListById();

	void addNewProduct(String userId, ProductVO product, List<MultipartFile> productImage);

	void likeProuct(String prdtId);

	void unlikeProuct(String prdtId);

	void deleteProduct(String prdtId);
	
	void updateProduct(ProductVO product, List<MultipartFile> productImage);
	
	int getProductCount(Criteria cri);
	
}
