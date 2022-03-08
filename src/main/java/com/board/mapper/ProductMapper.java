package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.board.domain.Criteria;
import com.board.domain.ProductVO;

public interface ProductMapper {

	ProductVO getProductById(String prdtId);

	List<ProductVO> getProductList();
	
	List<ProductVO> getProductListById(String accountId);

	void addNewProduct(ProductVO product);

	void deleteProduct(@Param("accountId") String accountId, @Param("prdtId") String prdtId);

	void likeProuct(String prdtId);
	
	void unlikeProuct(String prdtId);
	
	void addLikeProduct(ProductVO product);
	
	void deleteLikeProduct(ProductVO product);
	
	void updateProduct(ProductVO product);
	
	List<ProductVO> getListWithPaging(Criteria cri);
	
	int getProductCount();
	
}
