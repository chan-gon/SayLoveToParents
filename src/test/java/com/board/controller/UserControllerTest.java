package com.board.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.web.context.WebApplicationContext;

import com.board.domain.UserVO;
import com.board.util.PasswordEncryptor;
import com.google.gson.Gson;

import lombok.Setter;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class UserControllerTest {
	
	private static final String DEFAULT_PWD = "test";
	
	@Setter(onMethod_ = { @Autowired })
	private WebApplicationContext ctx;

	private MockMvc mockMvc;
	
	public UserVO testUser;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Before
	public void setUp() {
		testUser = UserVO.builder()
				.userId("test")
				.userPwd(PasswordEncryptor.encrypt(DEFAULT_PWD))
				.userName("test")
				.userEmail("test@gmail.com")
				.userPhone("01077777777")
				.userAddr("미국 조지아")
				.build();
	}
	
	@Test
	public void 회원가입_테스트_실패() throws Exception {
		String jsonStr = new Gson().toJson(testUser);
		
		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
				.andExpect(status().isConflict());
	}
	
	@Test
	public void 회원가입_테스트_성공() throws Exception {
		String jsonStr = new Gson().toJson(testUser);
		
		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void 아이디_종복_확인_실패_중복아이디_없음() throws Exception {
		mockMvc.perform(get("/users/signup/id")
				.param("userId", "none")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void 아이디_종복_확인_성공_중복아이디_있음() throws Exception {
		mockMvc.perform(get("/users/signup/id")
				.param("userId", testUser.getUserId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
	
	@Test
	public void 이메일_중복_확인_실패_중목이메일_없음() throws Exception {
		mockMvc.perform(get("/users/signup/email")
				.param("userEmail", "none")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void 이메일_중복_확인_성공_중복이메일_있음() throws Exception {
		mockMvc.perform(get("/users/signup/email")
				.param("userEmail", testUser.getUserEmail())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isConflict());
	}
	
	@Test
	public void 아이디_찾기_테스트() throws Exception {
		// 성공
		String jsonStr1 = new Gson().toJson(testUser);
		
		mockMvc.perform(post("/users/help/id")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr1))
				.andDo(print())
				.andExpect(status().isOk());
		
		// 실패
		String jsonStr2 = new Gson().toJson(null);
		
		mockMvc.perform(post("/users/help/id")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr2))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 사용자_탈퇴_테스트() throws Exception {
		// 실패
		String jsonStr1 = new Gson().toJson(null);
		
		mockMvc.perform(delete("/users/{userId}", testUser.getUserId())
				.param("userPwd", DEFAULT_PWD)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr1))
				.andDo(print())
				.andExpect(status().isBadRequest());

		// 성공
		String jsonStr2 = new Gson().toJson(testUser);
		
		mockMvc.perform(delete("/users/{userId}", testUser.getUserId())
				.param("userPwd", DEFAULT_PWD)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr2))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
