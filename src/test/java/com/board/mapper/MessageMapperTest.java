package com.board.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.ImageVO;
import com.board.domain.MessageVO;
import com.board.domain.ProductVO;
import com.board.domain.UserVO;

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
public class MessageMapperTest {
	
	private final static String TEST_VALUE = "testVal";
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ImageMapper imageMapper;
	
	private ProductVO product;
	
	private MessageVO message;
	
	private ImageVO image;
	
	private UserVO buyer;
	
	private UserVO seller;
	
	@Before
	public void setUp() {
		buyer = UserVO.builder()
				.userId("buyer")
				.userPwd(TEST_VALUE)
				.userName("buyer")
				.userEmail("testVal@gmail.com")
				.userPhone("01012341234")
				.userAddr(TEST_VALUE)
				.build();
		
		seller = UserVO.builder()
				.userId("seller")
				.userPwd(TEST_VALUE)
				.userName("seller")
				.userEmail("testVal@gmail.com")
				.userPhone("01012341234")
				.userAddr(TEST_VALUE)
				.build();
		
		userMapper.signUpUser(buyer);
		userMapper.signUpUser(seller);
		
		UserVO sellerInfo = userMapper.getUserById(seller.getUserId());
		
		product = ProductVO.builder()
				.prdtId(TEST_VALUE)
				.accountId(sellerInfo.getAccountId())
				.prdtName("아이폰")
				.prdtPrice(TEST_VALUE)
				.prdtCategory("디지털")
				.prdtInfo(TEST_VALUE)
				.prdtCondition(TEST_VALUE)
				.prdtIsTradeable(TEST_VALUE)
				.prdtIsDeliveryFree(TEST_VALUE)
				.prdtTradeLoc(TEST_VALUE)
				.build();
		
		image = ImageVO.builder()
				.fileId(TEST_VALUE)
				.prdtId(TEST_VALUE)
				.fileName(TEST_VALUE)
				.build();
		
		productMapper.addNewProduct(product);
		imageMapper.addImages(image);
	}
	
	@Test
	public void insert_sendMessage() {
		ProductVO insertProduct = productMapper.getProductById(product.getPrdtId());
		message = MessageVO.builder()
				.prdtId(insertProduct.getPrdtId())
				.buyer(buyer.getUserId())
				.seller(seller.getUserId())
				.content(TEST_VALUE)
				.type("BUYER")
				.build();
		messageMapper.sendMessage(message);
	}
	
	@Test
	public void insert_sendResponse() {
		ProductVO insertProduct = productMapper.getProductById(product.getPrdtId());
		message = MessageVO.builder()
				.prdtId(insertProduct.getPrdtId())
				.buyer(buyer.getUserId())
				.seller(seller.getUserId())
				.content(TEST_VALUE)
				.type("SELLER")
				.build();
		messageMapper.sendResponse(message);
	}
	
	@Test
	public void select_getSentMsgList() {
		ProductVO insertProduct = productMapper.getProductById(product.getPrdtId());
		message = MessageVO.builder()
				.prdtId(insertProduct.getPrdtId())
				.buyer(buyer.getUserId())
				.seller(seller.getUserId())
				.content("Hello, I'm a buyer")
				.type("BUYER")
				.build();
		messageMapper.sendMessage(message);
		messageMapper.getSentMsgList(buyer.getUserId(), seller.getUserId());
	}
	
	@Test
	public void select_getReceivedMsgList() {
		ProductVO insertProduct = productMapper.getProductById(product.getPrdtId());
		message = MessageVO.builder()
				.prdtId(insertProduct.getPrdtId())
				.buyer(buyer.getUserId())
				.seller(seller.getUserId())
				.content("Hello, I'm a buyer")
				.type("BUYER")
				.build();
		messageMapper.sendMessage(message);
		messageMapper.getReceivedMsgList(seller.getUserId(), buyer.getUserId());
	}
	
	@Test
	public void select_getMessagesById() {
		ProductVO insertProduct = productMapper.getProductById(product.getPrdtId());
		for (int i = 1; i < 6; i++) {
			message = MessageVO.builder()
					.prdtId(insertProduct.getPrdtId())
					.buyer(buyer.getUserId())
					.seller(seller.getUserId())
					.content("Hello, I'm a buyer" + i)
					.type("BUYER")
					.build();
			messageMapper.sendMessage(message);
		}
		messageMapper.getMessagesById(product.getPrdtId());
	}
	
	@Test
	public void delete_deleteMessagesById() {
		ProductVO insertProduct = productMapper.getProductById(product.getPrdtId());
		for (int i = 1; i < 6; i++) {
			message = MessageVO.builder()
					.prdtId(insertProduct.getPrdtId())
					.buyer(buyer.getUserId())
					.seller(seller.getUserId())
					.content("Hello, I'm a buyer" + i)
					.type("BUYER")
					.build();
			messageMapper.sendMessage(message);
		}
		messageMapper.deleteMessagesById(product.getPrdtId());
	}
	
	@Test
	public void delete_deleteMessagesPermanent() {
		ProductVO insertProduct = productMapper.getProductById(product.getPrdtId());
		for (int i = 1; i < 6; i++) {
			message = MessageVO.builder()
					.prdtId(insertProduct.getPrdtId())
					.buyer(buyer.getUserId())
					.seller(seller.getUserId())
					.content("Hello, I'm a buyer" + i)
					.type("BUYER")
					.build();
			messageMapper.sendMessage(message);
		}
		messageMapper.deleteMessagesPermanent(buyer.getUserId());
	}

}
