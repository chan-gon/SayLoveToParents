package com.board.service;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.board.domain.Criteria;
import com.board.domain.ImageVO;
import com.board.domain.ProductVO;
import com.board.domain.UserVO;
import com.board.exception.product.DeleteProductException;
import com.board.exception.product.InsertProductException;
import com.board.exception.product.ProductExceptionMessage;
import com.board.exception.product.ProductNotFoundException;
import com.board.exception.product.UpdateProductException;
import com.board.mapper.ImageMapper;
import com.board.mapper.ProductMapper;
import com.board.mapper.UserMapper;
import com.board.util.ImageFileUtils;
import com.board.util.LoginUserUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final UserMapper userMapper;
	
	private final ProductMapper productMapper;
	
	private final ImageMapper imageMapper;
	
	/**
	 * 	파일 등록 작업에 productId가 사용되기 때문에 테이블 PK인 productId의 난수값 생성을 
	 * 	DB 내부가 아닌 비즈니스 로직에서 생성하도록 했다.
	 *	
	 */
	@Override
	@Transactional
	public void addNewProduct(String userId, ProductVO product, List<MultipartFile> productImage) {
		try {
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
			for (MultipartFile image : productImage) {
				String filePath = ImageFileUtils.getFilePath();
				String fileName = ImageFileUtils.getFileName(image);
				ImageFileUtils.saveImages(filePath, fileName, image);
				ImageVO newImage = ImageVO.builder()
						.prdtId(productId)
						.fileName(fileName)
						.filePath(filePath)
						.build();
				imageMapper.addImages(newImage);
			}
//			// 이미지 등록
//			for (int i = 0; i < productImage.size(); i++) {
//				String filePath = ImageFileUtils.getFilePath();
//				String fileName = ImageFileUtils.getFileName(productImage.get(i));
//				ImageFileUtils.saveImages(filePath, fileName, productImage.get(i));
//				ImageVO newImage = ImageVO.builder()
//						.prdtId(productId)
//						.fileName(fileName)
//						.filePath(filePath)
//						.build();
//				imageMapper.addImages(newImage);
//			}
		} catch (RuntimeException e) {
			throw new InsertProductException(ProductExceptionMessage.INSERT_FAIL);
		}
	}

	@Override
	public ProductVO getProductById(String prdtId) {
		try {
			ProductVO product = productMapper.getProductById(prdtId);
			if(product == null) {
				throw new ProductNotFoundException(ProductExceptionMessage.NOT_FOUND);
			}
			return product;
		} catch (RuntimeException e) {
			throw new ProductNotFoundException(ProductExceptionMessage.NOT_FOUND);
		}
	}

	@Override
	@Cacheable(value = "productListCache", keyGenerator = "keyGenerator")
	public List<ProductVO> getProductList(Criteria cri) {
		try {
			return productMapper.getListWithPaging(cri);
		} catch (RuntimeException e) {
			throw new ProductNotFoundException(ProductExceptionMessage.NOT_FOUND);
		}
	}

	@Override
	@Transactional
	public void likeProuct(String prdtId) {
		try {
			String currentUserId = LoginUserUtils.getUserId();
			String accountId = userMapper.getAccountId(currentUserId);
			ProductVO addLikeProduct = ProductVO.builder()
					.prdtId(prdtId)
					.accountId(accountId)
					.build();
			productMapper.likeProuct(prdtId);
			productMapper.addLikeProduct(addLikeProduct);
		} catch (RuntimeException e) {
			throw new UpdateProductException(ProductExceptionMessage.UPDATE_FAIL);
		}
	}

	@Override
	@Transactional
	public void unlikeProuct(String prdtId) {
		try {
			String currentUserId = LoginUserUtils.getUserId();
			String accountId = userMapper.getAccountId(currentUserId);
			ProductVO deleteLikeProduct = ProductVO.builder()
					.prdtId(prdtId)
					.accountId(accountId)
					.build();
			productMapper.unlikeProuct(prdtId);
			productMapper.deleteLikeProduct(deleteLikeProduct);
		} catch (RuntimeException e) {
			throw new UpdateProductException(ProductExceptionMessage.UPDATE_FAIL);
		}
	}
	
	@Override
	public List<ProductVO> getLikeProduct() {
		String currentUserId = LoginUserUtils.getUserId();
		String accountId = userMapper.getAccountId(currentUserId);
		return productMapper.getLikeProduct(accountId);
	}

	@Override
	public List<ProductVO> getProductListById() {
		try {
			String userId = LoginUserUtils.getUserId();
			String accountId = userMapper.getAccountId(userId);
			return productMapper.getProductListById(accountId);
		} catch (RuntimeException e) {
			throw new ProductNotFoundException(ProductExceptionMessage.NOT_FOUND);
		}
	}

	@Override
	@Transactional
	public void deleteProduct(String prdtId) {
		try {
			String userId = LoginUserUtils.getUserId();
			String accountId = userMapper.getAccountId(userId);
			List<ImageVO> localImages = imageMapper.getImagesById(prdtId);
			for (int i = 0; i < localImages.size(); i++) {
				ImageFileUtils.deleteImages(localImages.get(i));
			}
			imageMapper.deleteImages(prdtId);
			productMapper.deleteProduct(accountId, prdtId);
		} catch (RuntimeException e) {
			throw new DeleteProductException(ProductExceptionMessage.DELETE_FAIL);
		}
	}

	@Override
	@Transactional
	public void updateProduct(ProductVO product, List<MultipartFile> productImage) {
		try {
			ProductVO updateProduct = ProductVO.builder()
					.prdtId(product.getPrdtId())
					.prdtName(product.getPrdtName())
					.prdtPrice(product.getPrdtPrice())
					.prdtCategory(product.getPrdtCategory())
					.prdtTradeLoc(product.getPrdtTradeLoc())
					.prdtCondition(product.getPrdtCondition())
					.prdtIsTradeable(product.getPrdtIsTradeable())
					.prdtIsDeliveryFree(product.getPrdtIsDeliveryFree())
					.prdtInfo(product.getPrdtInfo())
					.build();
			productMapper.updateProduct(updateProduct);
			
			String prdtId = product.getPrdtId();
			if (!productImage.isEmpty()) {
				List<ImageVO> localImages = imageMapper.getImagesById(prdtId);
				for (ImageVO image : localImages) {
					ImageFileUtils.deleteImagesPermanent(image.getFileName());
				}
				imageMapper.deleteImages(prdtId);
				
				// 업데이트 이미지 등록
				for (int i = 0; i < productImage.size(); i++) {
					String filePath = ImageFileUtils.getFilePath();
					String fileName = ImageFileUtils.getFileName(productImage.get(i));
					ImageFileUtils.saveImages(filePath, fileName, productImage.get(i));
					ImageVO newImage = ImageVO.builder()
							.prdtId(prdtId)
							.fileName(fileName)
							.filePath(filePath)
							.build();
					imageMapper.addImages(newImage);
				}
			}
		} catch (RuntimeException e) {
			throw new UpdateProductException(ProductExceptionMessage.UPDATE_FAIL);
		}
	}

	@Override
	public int getProductCount(Criteria cri) {
		try {
			return productMapper.getProductCount(cri);
		} catch (RuntimeException e) {
			throw new ProductNotFoundException(ProductExceptionMessage.NOT_FOUND);
		}
	}

}
