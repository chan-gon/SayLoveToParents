package com.board.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.board.domain.ProductVO;
import com.board.domain.UserVO;
import com.board.exception.file.ImageUploadFailException;
import com.board.exception.product.ProductNotFoundException;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Transactional
public class ProductServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	private UserVO user;
	
	private ProductVO product;
	
	@Before
	public void setUpUser() {
		user = UserVO.builder()
				.userId("changon")
				.userPwd("changon1234")
				.userName("홍길동")
				.userEmail("changon@gmail.com")
				.userPhone("01012341234")
				.userAddr("대구 광역시 동구")
				.build();
	}
	
	@Before
	public void setUpProduct() {
		product = ProductVO.builder()
				.prdtName("CD플레이어")
				.prdtPrice("777")
				.prdtCategory("디지털")
				.prdtInfo("aiwa CD 플레이어 팔아요.")
				.prdtCondition("새상품")
				.prdtIsTradeable("교환불가")
				.prdtIsDeliveryFree("배송비별도")
				.prdtLikeCnt(0)
				.prdtTradeLoc("대구 중구")
				.build();
	}
	
	public void createUserOne() {
		userService.signUpUser(user);
	}
	public void createUserTwo() {
		user = UserVO.builder()
				.userId("bang")
				.userPwd("bang1234")
				.userName("이방원")
				.userEmail("bang@gmail.com")
				.userPhone("01002420404")
				.userAddr("대구 로스 엔젤리너스")
				.build();
		userService.signUpUser(user);
	}
	public void createProduct() {
		createUserOne();
		List<MultipartFile> files = Arrays.asList(
				new MockMultipartFile("productImage1", "CDPlayer1.jpeg", "image/jpeg", "<<jpeg data>>".getBytes()),
				new MockMultipartFile("productImage2", "CDPlayer2.jpeg", "image/jpeg", "<<jpeg data>>".getBytes())
				);
		productService.addNewProduct(user.getUserId(), product, files);
	}
	
	@Test
	public void Successfully_create_product() {
		// given
		createUserOne();
		List<MultipartFile> files = Arrays.asList(
				new MockMultipartFile("productImage1", "CDPlayer1.jpeg", "image/jpeg", "<<jpeg data>>".getBytes()),
				new MockMultipartFile("productImage2", "CDPlayer2.jpeg", "image/jpeg", "<<jpeg data>>".getBytes())
				);
		
		// when
		productService.addNewProduct(user.getUserId(), product, files);
		
		// then
		assertThat(productService.getProductIdByName(product.getPrdtName()), is(not(nullValue())));
	}
	
	@Test
	public void Cannot_create_product_without_image() {
		// then
		exceptionRule.expect(ImageUploadFailException.class);
		exceptionRule.expectMessage("업로드된 이미지가 없습니다.");
		
		// given
		createUserOne();
		List<MultipartFile> files = Arrays.asList();
		
		// when
		productService.addNewProduct(user.getUserId(), product, files);
	}
	
	@Test
	public void Able_to_see_an_existed_product() {
		// given
		createProduct();
		ProductVO insertProduct = productService.getProductIdByName(product.getPrdtName());
		
		// when
		ProductVO calledProduct = productService.getProductById(insertProduct.getPrdtId());
		
		// then
		assertThat(calledProduct, is(not(nullValue())));
	}
	
	@Test
	public void Cannot_find_the_product() {
		// then
		exceptionRule.expect(ProductNotFoundException.class);

		// given
		createProduct();
		
		// when
		productService.getProductById(null);
	}
	
	@Test
	@WithMockUser(username = "bang", password = "bang1234", authorities = {"ROLE_USER"})
	public void Able_to_like_product() {
		// given
		createProduct();
		createUserTwo();
		ProductVO insertProduct = productService.getProductIdByName(product.getPrdtName());
		
		// when
		productService.likeProuct(insertProduct.getPrdtId());
		
		// then
		assertThat(productService.getProductById(insertProduct.getPrdtId()).getPrdtLikeCnt(), is(1));
	}
	
	@Test
	@WithMockUser(username = "bang", password = "bang1234", authorities = {"ROLE_USER"})
	public void Able_to_unlike_product() {
		// given
		createProduct();
		createUserTwo();
		ProductVO insertProduct = productService.getProductIdByName(product.getPrdtName());
		
		// when
		productService.likeProuct(insertProduct.getPrdtId());
		productService.unlikeProuct(insertProduct.getPrdtId());
		
		// then
		assertThat(productService.getProductById(insertProduct.getPrdtId()).getPrdtLikeCnt(), is(0));
	}
	
	@Test
	public void Successfully_update_product() {
		// given
		createProduct();
		ProductVO insertProduct = productService.getProductIdByName(product.getPrdtName());
		product = ProductVO.builder()
				.prdtId(insertProduct.getPrdtId())
				.prdtName("CD플레이어")
				.prdtPrice("789")
				.prdtCategory("디지털")
				.prdtInfo("aiwa CD 플레이어 팔아요.")
				.prdtCondition("새상품")
				.prdtIsTradeable("교환불가")
				.prdtIsDeliveryFree("배송비포함")
				.prdtLikeCnt(0)
				.prdtTradeLoc("대구 북구")
				.build();
		List<MultipartFile> files = Arrays.asList(
				new MockMultipartFile("productImage1", "CDPlayer1.jpeg", "image/jpeg", "<<jpeg data>>".getBytes()),
				new MockMultipartFile("productImage2", "CDPlayer2.jpeg", "image/jpeg", "<<jpeg data>>".getBytes())
				);
		// when
		productService.updateProduct(product, files);
		
		// then
		ProductVO updateProduct = productService.getProductById(product.getPrdtId());
		assertThat(updateProduct.getPrdtIsDeliveryFree(), is("배송비포함"));
		assertThat(updateProduct.getPrdtTradeLoc(), is("대구 북구"));
	}
	
	@Test
	@WithMockUser(username = "changon", password = "changon1234", authorities = {"ROLE_USER"})
	public void Successfully_delete_product() {
		// then
		exceptionRule.expect(ProductNotFoundException.class);

		// given
		createProduct();
		ProductVO insertProduct = productService.getProductIdByName(product.getPrdtName());
		
		// when
		productService.deleteProduct(insertProduct.getPrdtId());
		productService.getProductById(insertProduct.getPrdtId());
	}
}