package com.board.service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.board.domain.ImageVO;
import com.board.domain.ProductVO;
import com.board.domain.UserVO;
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
			FileUtils.saveImages(filePath, fileName, productImage.get(i));
			ImageVO newImage = ImageVO.builder()
					.prdtId(productId)
					.fileName(fileName)
					.filePath(filePath)
					.build();
			imageMapper.addImages(newImage);
		}
	}

	@Override
	public ProductVO getProductById(String accountId) {
		return productMapper.getProductById(accountId);
	}

	@Override
	public List<ProductVO> getProductList() {
		return productMapper.getProductList();
	}

	@Override
	public void likeProuct(String prdtId) {
		productMapper.likeProuct(prdtId);
	}

	@Override
	public void unlikeProuct(String prdtId) {
		productMapper.unlikeProuct(prdtId);
	}

	@Override
	public List<ProductVO> getProductListById(Principal principal) {
		String userId = principal.getName();
		String accountId = userMapper.getAccountId(userId);
		return productMapper.getProductListById(accountId);
	}

	@Override
	@Transactional
	public void deleteProduct(Principal principal, String prdtId) {
		String userId = principal.getName();
		String accountId = userMapper.getAccountId(userId);
		productMapper.deleteProduct(accountId, prdtId);
		imageMapper.deleteImages(prdtId);
		List<ImageVO> localImages = imageMapper.getImagesById(prdtId);
		for (int i = 0; i < localImages.size(); i++) {
			FileUtils.deleteImages(localImages.get(i));
		}
	}
}
