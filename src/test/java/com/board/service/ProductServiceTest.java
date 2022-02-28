package com.board.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.board.domain.ProductVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ProductServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	public ProductVO testProduct;
	
	@Before
	public void setup() {
		testProduct = ProductVO.builder()
				.prdtName("test")
				.prdtPrice(777)
				.prdtCategory("신발")
				.prdtInfo("test")
				.prdtCondition("새상품")
				.prdtIsTradeable("교환불가")
				.prdtIsDeliveryFree("배송비포함")
				.prdtTradeLoc("하와이")
				.build();
	}
	

}
