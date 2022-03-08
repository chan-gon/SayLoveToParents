package com.board.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.board.domain.Criteria;
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
	
	@Test
	public void testGetList() {
		productService.getProductList(new Criteria(1, 10)).forEach(board -> log.info(board));
	}

}
