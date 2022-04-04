package com.board.mapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.Criteria;
import com.board.domain.ImageVO;
import com.board.domain.ProductVO;
import com.board.domain.UserVO;

import lombok.extern.log4j.Log4j;

/**
 * User 데이터를 다루는 쿼리가 제대로 작성 되었는지 / 주어진 환경에서 제대로 동작하는지 확인.
 * 
 * 테스트 네이밍 규칙 : [SQL 작업 내용]_[테스트 내용(Mapper 클래스 메소드 이름을 사용했음)]
 * 
 * @Transactional : 트랜잭션 테스트 후 트랜잭션을 강제 롤백해서 DB에 반영되지 않도록 한다.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Transactional
@Log4j
public class ProductMapperTest {
	
	private final static String TEST_VALUE = "testVal";

	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ImageMapper imageMapper;

	private Criteria cri = new Criteria();

	private ProductVO product;
	
	private UserVO user;
	
	private ImageVO image;

	/* user 정보가 있어야만 product 등록이 가능하기 때문에
	 * user 정보를 생성 -> product 등록 순서로 진행.
	 */
	@Before
	public void insert_addNewProduct() {
		// user 생성
		user = UserVO.builder()
				.userId(TEST_VALUE)
				.userPwd(TEST_VALUE)
				.userName(TEST_VALUE)
				.userEmail("testVal@gmail.com")
				.userPhone("01012341234")
				.userAddr(TEST_VALUE)
				.build();
		userMapper.signUpUser(user);
		
		// 생성한 user 정보 가져오기
		UserVO insertUser = userMapper.getUserById(user.getUserId());
		
		// product 생성 준비
		product = ProductVO.builder()
				.prdtId(TEST_VALUE)
				.accountId(insertUser.getAccountId())
				.prdtName("아이폰")
				.prdtPrice(TEST_VALUE)
				.prdtCategory("디지털")
				.prdtInfo(TEST_VALUE)
				.prdtCondition(TEST_VALUE)
				.prdtIsTradeable(TEST_VALUE)
				.prdtIsDeliveryFree(TEST_VALUE)
				.prdtTradeLoc(TEST_VALUE)
				.build();
		
		// image 생성 준비
		image = ImageVO.builder()
				.fileId(TEST_VALUE)
				.prdtId(TEST_VALUE)
				.fileName(TEST_VALUE)
				.build();
		
		// product 생성
		productMapper.addNewProduct(product);
		imageMapper.addImages(image);
	}

	@Test
	public void select_getListWithPaging() {
		cri.setPageNum(2);
		cri.setAmount(10);
		List<ProductVO> list = productMapper.getListWithPaging(cri);
		list.forEach(board -> log.info(board));
	}

	@Test
	public void select_getListWithPagingByCategory() {
		cri.setPageNum(1);
		cri.setAmount(10);
		cri.setCategory("디지털");
		List<ProductVO> list = productMapper.getListWithPaging(cri);
		list.forEach(board -> log.info(board));
	}

	@Test
	public void select_getListWithPagingByKeyword() {
		cri.setPageNum(1);
		cri.setAmount(10);
		cri.setKeyword("나이키");
		List<ProductVO> list = productMapper.getListWithPaging(cri);
		list.forEach(board -> log.info(board));
	}
	
	@Test
	public void select_getProductById() {
		// given
		// 상단의 insert_addNewProduct()
		
		// when
		ProductVO existProduct = productMapper.getProductById(product.getPrdtId());
		
		// then
		assertThat(existProduct, is(not(nullValue())));
	}
	
	@Test
	public void select_getProductList() {
		// given
		// 상단의 insert_addNewProduct()
		
		// when
		List<ProductVO> productList = productMapper.getProductList();
		
		// then
		assertThat(productList, is(not(nullValue())));
	}
	
	@Test
	public void update_likeProuct_unlikeProuct() {
		// given
		// 상단의 insert_addNewProduct()
		
		// when
		productMapper.likeProuct(product.getPrdtId());
		ProductVO insertProduct1 = productMapper.getProductById(product.getPrdtId());
		
		// then
		assertEquals(1, insertProduct1.getPrdtLikeCnt());
		
		// when
		productMapper.unlikeProuct(product.getPrdtId());
		ProductVO insertProduct2 = productMapper.getProductById(product.getPrdtId());
		
		// then
		assertEquals(0, insertProduct2.getPrdtLikeCnt());
	}
	
	@Test
	public void select_getProductId() {
		// given
		// 상단의 insert_addNewProduct()
		
		// when
		List<ProductVO> productIds = productMapper.getProductId(user.getAccountId());
		
		// then
		assertThat(productIds, is(not(nullValue())));
	}
	
	@Test
	public void update_updateProduct() {
		// given
		ProductVO insertProduct = productMapper.getProductById(product.getPrdtId());
		String updateVal  = "update";
		product = ProductVO.builder()
				.prdtId(insertProduct.getPrdtId())
				.prdtName(updateVal)
				.prdtPrice(updateVal)
				.prdtCategory(updateVal)
				.prdtTradeLoc(updateVal)
				.prdtCondition(updateVal)
				.prdtIsTradeable(updateVal)
				.prdtIsDeliveryFree(updateVal)
				.prdtInfo(updateVal)
				.build();
		
		// when
		productMapper.updateProduct(product);
		
		// then
		ProductVO updateProduct = productMapper.getProductById(product.getPrdtId());
		assertThat(updateProduct.getPrdtName(), is(updateVal));
	}
	
	@Test
	public void insert_addLikeProduct() {
		// given
		// 상단의 insert_addNewProduct()
		
		// when
		productMapper.addLikeProduct(product);
		
		// then
		List<ProductVO> likeProducts = productMapper.getLikeProduct(userMapper.getAccountId(user.getUserId()));
		assertThat(likeProducts, is(not(nullValue())));
	}
	
	@Test
	public void delete_deleteLikeProduct() {
		// given
		// 상단의 insert_addNewProduct()
		
		// when
		productMapper.addLikeProduct(product);
		productMapper.deleteLikeProduct(product);
		
		// then
		List<ProductVO> likeProducts = productMapper.getLikeProduct(userMapper.getAccountId(user.getUserId()));
		assertThat(likeProducts.size(), is(0));
	}
	
	@Test
	public void delete_deleteProduct() {
		// given
		// 상단의 insert_addNewProduct()
		
		// when
		imageMapper.deleteImages(product.getPrdtId());
		productMapper.deleteProduct(product.getAccountId(), product.getPrdtId());
		
		// then
		ProductVO insertProduct = productMapper.getProductById(product.getPrdtId());
		assertThat(insertProduct, is(nullValue()));
	}
	
	@Test
	public void delete_deleteProductPermanent() {
		// given
		// 상단의 insert_addNewProduct()
		
		// when
		imageMapper.deleteImages(product.getPrdtId());
		productMapper.deleteProductPermanent(userMapper.getAccountId(user.getUserId()));
		
		// then
		ProductVO insertProduct = productMapper.getProductById(product.getPrdtId());
		assertThat(insertProduct, is(nullValue()));
	}
	
	@Test
	public void delete_deleteProductLikePermanent() {
		// given
		// 상단의 insert_addNewProduct()
		
		// when
		productMapper.addLikeProduct(product);
		productMapper.deleteProductLikePermanent(product.getPrdtId());
		
		// then
		List<ProductVO> likeProducts = productMapper.getLikeProduct(userMapper.getAccountId(user.getUserId()));
		assertThat(likeProducts.size(), is(0));
	}
}
