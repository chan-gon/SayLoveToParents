package com.board.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.print.AttributeException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { com.board.config.RootConfig.class, com.board.config.ServletConfig.class })
@Log4j
public class UserControllerTest {

	@Setter(onMethod_ = { @Autowired })
	private WebApplicationContext ctx;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	@Test
	public void 사용자_생성_테스트() throws Exception {
		mockMvc.perform(post("/user/delete-user").param("userId", "test"));
		
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/user/signup")
				.param("userId", "test")
				.param("userPwd", "1234")
				.param("userName", "김토마토")
				.param("userEmail", "tomato@naver.com")
				.param("userPhone", "010-7979-7979")
				.param("userAddr", "서울시 동대문구")
				).andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	
}
