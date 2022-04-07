package com.board.service;

import static org.hamcrest.CoreMatchers.is;
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

import com.board.domain.MessageVO;
import com.board.domain.ProductVO;
import com.board.domain.UserVO;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Transactional
public class MessageServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MessageService messageService;
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	private UserVO user;
	
	private ProductVO product;
	
	private MessageVO message;
	
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
	@WithMockUser(username = "bang", password = "bang1234", authorities = {"ROLE_USER"})
	public void Successfully_send_message() {
		// given
		createProduct();
		createUserTwo();
		ProductVO insertProduct = productService.getProductIdByName(product.getPrdtName());
		message = MessageVO.builder()
				.prdtId(insertProduct.getPrdtId())
				.buyer("bang")
				.seller("changon")
				.content("메시지 전송")
				.type("BUYER")
				.build();
		
		// when
		messageService.sendMessage(message);
		List<MessageVO> receivedMsg = messageService.getSentMsgList("bang", "changon");
		
		// then
		assertThat(receivedMsg.get(0).getBuyer(), is("bang"));
		assertThat(receivedMsg.get(0).getSeller(), is("changon"));
		assertThat(receivedMsg.get(0).getContent(), is("메시지 전송"));
	}
	
	@Test
	public void Successfully_send_response() {
		// given
		createProduct();
		createUserTwo();
		ProductVO insertProduct = productService.getProductIdByName(product.getPrdtName());
		message = MessageVO.builder()
				.prdtId(insertProduct.getPrdtId())
				.buyer("bang")
				.seller("changon")
				.content("답장 전송")
				.type("BUYER")
				.build();
				
		// when
		messageService.sendResponse(message);
		List<MessageVO> receivedMsg = messageService.getReceivedMsgList("changon", "bang");
		
		// then
		assertThat(receivedMsg.get(0).getBuyer(), is("bang"));
		assertThat(receivedMsg.get(0).getSeller(), is("changon"));
		assertThat(receivedMsg.get(0).getContent(), is("답장 전송"));
	}

}
