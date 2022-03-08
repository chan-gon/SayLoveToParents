package com.board.mapper;

import java.util.List;

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
public class ProductMapperTests {
	
	@Autowired
	private ProductMapper productMapper;

	@Test
	public void testPaging() {
		Criteria cri = new Criteria();
		cri.setPageNum(2);
		cri.setAmount(10);
		List<ProductVO> list = productMapper.getListWithPaging(cri);
		log.info("=========================== " + list);
		list.forEach(board -> log.info(board));
	}
}
