package com.board.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.board.domain.ImageVO;
import com.board.domain.ProductVO;
import com.board.domain.UserVO;
import com.board.exception.ImageUploadFailException;
import com.board.mapper.ImageMapper;
import com.board.mapper.ProductMapper;
import com.board.mapper.UserMapper;
import com.board.util.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@RequiredArgsConstructor
@Log4j
public class ProductServiceImpl implements ProductService {
	
	private final UserMapper userMapper;
	
	private final ProductMapper productMapper;
	
	private final ImageMapper imageMapper;

	/**
	 *	userId를 통해 사용자 정보를 가져온 다음
	 *	accountId
	 */
	@Override
	@Transactional
	public void addNewProduct(String userId, ProductVO product, List<MultipartFile> productImage) {
		// 상품 등록
		UserVO userInfo = userMapper.getUserById(userId);
		String productId = UUID.randomUUID().toString().replace("-", "");
		ProductVO newProduct = ProductVO.builder()
				.prdtId(productId)
				.accountId(userInfo.getAccountId())
				.prdtName(product.getPrdtName())
				.prdtPrice(product.getPrdtPrice())
				.prdtCategory(product.getPrdtCategory())
				.prdtInfo(product.getPrdtInfo())
				.prdtCondition(product.getPrdtCondition())
				.prdtIsTradeable(product.getPrdtIsTradeable())
				.prdtIsDeliveryFree(product.getPrdtIsDeliveryFree())
				.prdtTradeLoc(product.getPrdtTradeLoc())
				.build();
		productMapper.addNewProduct(newProduct);
		
		// 이미지 등록
		for (int i = 0; i < productImage.size(); i++) {
			String filePath = FileUtils.getFilePath();
			String fileName = FileUtils.getFileName(productImage.get(i));
			ImageVO newImage = ImageVO.builder()
					.prdtId(productId)
					.fileName(fileName)
					.filePath(filePath)
					.build();
			imageMapper.addImages(newImage);
			FileUtils.saveImages(filePath, productImage.get(i));
		}
	}

	@Override
	public List<ProductVO> getProductById(String accountId) {
		return productMapper.getProductById(accountId);
	}

}
