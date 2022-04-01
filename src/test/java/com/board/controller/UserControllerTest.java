package com.board.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.board.domain.UserVO;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Transactional
public class UserControllerTest {

	@Rule 
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Autowired
	private UserController userController;
	
	@Autowired 
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	private RestDocumentationResultHandler document;
	
	private UserVO user;
	
	@Before
	public void setUp() throws ServletException {
		this.document = document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(document).build();
	}

	@Before
	public void setUpMockMvc() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@Before
	public void setUpUser() throws ParseException {
		user = UserVO.builder()
				.accountId("accountId")
				.userId("changon")
				.userPwd("changon1234")
				.userName("홍길동")
				.userEmail("changon@gmail.com")
				.userPhone("01012341234")
				.userAddr("대구 광역시 동구")
				.build();
	}
	
	@Test
	public void Successfully_create_new_user() throws Exception {
		String jsonStr = new Gson().toJson(user);
		
		mockMvc.perform(RestDocumentationRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
		.andExpect(status().isOk())
		.andDo(document.document(requestFields(
				fieldWithPath("accountId").type(JsonFieldType.STRING).description("유저 고유 번호"),
				fieldWithPath("userId").type(JsonFieldType.STRING).description("아이디"),
				fieldWithPath("userPwd").type(JsonFieldType.STRING).description("비밀번호"),
				fieldWithPath("userName").type(JsonFieldType.STRING).description("이름"),
				fieldWithPath("userEmail").type(JsonFieldType.STRING).description("이메일"),
				fieldWithPath("userPhone").type(JsonFieldType.STRING).description("연락처"),
				fieldWithPath("userAddr").type(JsonFieldType.STRING).description("주소")
				)));
	}
	
	@Test
	public void Can_use_this_email() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders.get("/users/signup/email").param("userEmail", user.getUserEmail()))
		.andExpect(status().isOk())
		.andDo(document.document(
				requestParameters(parameterWithName("userEmail").description("사용자 이메일"))));
	}
	
	@Test
	public void Can_use_this_id() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders.get("/users/signup/id").param("userId", user.getUserId()))
		.andExpect(status().isOk())
		.andDo(document.document(requestParameters(parameterWithName("userId").description("사용자 아이디"))));
	}
	
	@Test
	public void Able_to_retrieve_id() throws Exception {
		Successfully_create_new_user();
		
		user = UserVO.builder()
				.userName("홍길동")
				.userPhone("01012341234")
				.build();
		
		String jsonStr = new Gson().toJson(user);
		MvcResult result = mockMvc.perform(RestDocumentationRequestBuilders.post("/users/help/id")
				.contentType(MediaType.APPLICATION_JSON).content(jsonStr))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document.document(
				requestFields(
					fieldWithPath("userName").type(JsonFieldType.STRING).description("이름"),
					fieldWithPath("userPhone").type(JsonFieldType.STRING).description("연락처")
					)))
		.andReturn();
		
		String response = result.getResponse().getContentAsString();
		assertThat(response, is("아이디를 찾았습니다: changon"));
	}
	
	@Test
	public void Successfully_change_user_password() throws Exception {
		Successfully_create_new_user();
		
		user = UserVO.builder()
				.userId(user.getUserId())
				.userPwd("newPwd")
				.build();
		
		String jsonStr = new Gson().toJson(user);
		mockMvc.perform(RestDocumentationRequestBuilders.post("/users/help/pwd")
				.contentType(MediaType.APPLICATION_JSON).content(jsonStr))
		.andExpect(status().isOk())
		.andDo(document.document(requestFields(
				fieldWithPath("userId").type(JsonFieldType.STRING).description("아이디"),
				fieldWithPath("userPwd").type(JsonFieldType.STRING).description("새로운 비밀번호")
				)));
	}
	
	@Test
	public void Successfully_change_user_profile() throws Exception {
		Successfully_create_new_user();
		
		user = UserVO.builder()
				.userId("changon")
				.userEmail("changon@gmail.com")
				.userPhone("01012341234")
				.userAddr("대구 광역시 동구")
				.build();
		
		String jsonStr = new Gson().toJson(user);
		
		mockMvc.perform(RestDocumentationRequestBuilders.post("/users/profile")
				.contentType(MediaType.APPLICATION_JSON).content(jsonStr))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document.document(requestFields(
				fieldWithPath("userId").type(JsonFieldType.STRING).description("아이디"),
				fieldWithPath("userEmail").type(JsonFieldType.STRING).description("이메일"),
				fieldWithPath("userPhone").type(JsonFieldType.STRING).description("연락처"),
				fieldWithPath("userAddr").type(JsonFieldType.STRING).description("주소")
				)));
	}
	
	@Test
	public void Permanently_delete_user() throws Exception {
		Successfully_create_new_user();
		
		user = UserVO.builder()
				.userId("changon")
				.userEmail("changon@gmail.com")
				.build();
		
		String jsonStr = new Gson().toJson(user);
		mockMvc.perform(RestDocumentationRequestBuilders.delete("/users")
				.contentType(MediaType.APPLICATION_JSON).content(jsonStr))
		.andExpect(status().isOk())
		.andDo(document.document(requestFields(
				fieldWithPath("userId").type(JsonFieldType.STRING).description("아이디"),
				fieldWithPath("userEmail").type(JsonFieldType.STRING).description("이메일")
				)));
	}
	
}
