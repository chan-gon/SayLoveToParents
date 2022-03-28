package com.board.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.UserVO;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Transactional
public class UserControllerTest {
	
	private final static String TEST_VALUE = "testVal";

	@Autowired
	private UserController userController;
	
	private MockMvc mockMvc;
	
	private UserVO user;
	
	@Before
	public void setUpMockMvc() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@Before
	public void setUp() {
		user = UserVO.builder()
				.accountId(TEST_VALUE)
				.userId(TEST_VALUE)
				.userPwd(TEST_VALUE)
				.userName(TEST_VALUE)
				.userEmail("testVal@gmail.com")
				.userPhone("01012341234")
				.userAddr(TEST_VALUE)
				.build();
	}
	
	@Test
	public void Successfully_create_new_user() throws Exception {
		String jsonStr = new Gson().toJson(user);
		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
		.andExpect(status().isOk());
	}
	
	@Test
	public void Can_use_this_email() throws Exception {
		mockMvc.perform(get("/users/signup/email").param("userEmail", user.getUserEmail()))
		.andExpect(status().isOk());
	}
	
	@Test
	public void Can_use_this_id() throws Exception {
		mockMvc.perform(get("/users/signup/id").param("userId", user.getUserId()))
		.andExpect(status().isOk());
	}
	
}
