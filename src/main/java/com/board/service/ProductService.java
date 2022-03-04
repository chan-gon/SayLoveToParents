package com.board.service;

import java.security.Principal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.board.domain.ProductVO;

public interface ProductService {

	ProductVO getProductById(String prdtId);

	List<ProductVO> getProductList();

	List<ProductVO> getProductListById(Principal principal);

	void addNewProduct(String userId, ProductVO product, List<MultipartFile> productImage);

	void likeProuct(String prdtId);

	void unlikeProuct(String prdtId);

	void deleteProduct(Principal principal, String prdtId);

}
