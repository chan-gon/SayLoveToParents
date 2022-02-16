package com.board.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.board.domain.UserVO;
import com.google.gson.Gson;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

	@Setter(onMethod_ = { @Autowired })
	private WebApplicationContext ctx;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void A_사용자_생성_테스트() throws Exception {
		UserVO user = new UserVO();
		user.setUserId("test");
		user.setUserPwd("1234");
		user.setUserName("김토마토");
		user.setUserEmail("tomato@naver.com");
		user.setUserPhone("01079797979");
		user.setUserAddr("서울시 동대문구");
		
		String jsonStr = new Gson().toJson(user);
		
		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void B_아이디_종복_확인_테스트() throws Exception {
		mockMvc.perform(get("/users/signup/test")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
	
	@Test
	public void C_이메일_중복_확인_테스트() throws Exception {
		mockMvc.perform(get("/users/signup/tomato@naver.com")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isConflict());
	}
	
	@Test
	public void D_회원_탈퇴_테스트() throws Exception {
		mockMvc.perform(delete("/users/test"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void 아이디_찾기_테스트() throws Exception {
		UserVO user = new UserVO();
		user.setUserName("Kim");
		user.setUserPhone("01079797979");
		
		String jsonStr = new Gson().toJson(user);
		
		mockMvc.perform(post("/users/help/id")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
}
