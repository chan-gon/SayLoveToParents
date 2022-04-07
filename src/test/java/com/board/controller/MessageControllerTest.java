package com.board.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.board.domain.MessageVO;
import com.board.domain.ProductVO;
import com.board.domain.UserVO;
import com.board.service.ProductService;
import com.google.gson.Gson;
/**
 * 테스트 메소드 이름은 Spring Rest Docs 설정 작업의 편의성을 위해 간단하게 작성했습니다.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Transactional
public class MessageControllerTest {
	
	@Rule 
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
	
	@Autowired 
	private WebApplicationContext context;
	
	@Autowired
	private MessageController messageController;
	
	@Autowired
	private ProductService productService;

	private MockMvc mockMvc;
	
	private RestDocumentationResultHandler document;
	
	private MessageVO message;
	
	private ProductVO product;
	
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
		this.mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
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
	
	@Test
	@WithMockUser(username = "bang", password = "bang1234", authorities = {"ROLE_USER"})
	public void sendMessage() throws Exception {
		// given
		// 회원1(changon) 생성 및 상품 등록
		createProduct();
		// 회원2(bang) 생성
		createUserTwo();
		// 등록한 상품 정보 호출
		ProductVO insertProduct = productService.getProductIdByName(product.getPrdtName());
		
		message = MessageVO.builder()
				.prdtId(insertProduct.getPrdtId())
				.buyer("bang")
				.seller("changon")
				.content("구매자가 판매자에게 메시지를 보냈습니다.")
				.type("BUYER")
				.build();
		
		String jsonStr = new Gson().toJson(message);
		
		// when
		mockMvc.perform(RestDocumentationRequestBuilders.post("/messages")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
		// then
		.andExpect(status().isOk())
		.andDo(document.document(requestFields(
				fieldWithPath("prdtId").type(JsonFieldType.STRING).description("판매중인 상품"),
				fieldWithPath("buyer").type(JsonFieldType.STRING).description("판매자"),
				fieldWithPath("seller").type(JsonFieldType.STRING).description("구매자"),
				fieldWithPath("content").type(JsonFieldType.STRING).description("메시지 내용"),
				fieldWithPath("type").type(JsonFieldType.STRING).description("판매자/구매자 구분")
				)));
	}
	
	@Test
	@WithMockUser(username = "changon", password = "changon1234", authorities = {"ROLE_USER"})
	public void sendResponse() throws Exception {
		// given
		// 회원1(changon) 생성 및 상품 등록
		createProduct();
		// 회원2(bang) 생성
		createUserTwo();
		// 등록한 상품 정보 호출
		ProductVO insertProduct = productService.getProductIdByName(product.getPrdtName());
		
		message = MessageVO.builder()
				.prdtId(insertProduct.getPrdtId())
				.buyer("bang")
				.seller("changon")
				.content("판매자가 구매자에게 메시지를 받아서 답장을 보냅니다.")
				.type("SELLER")
				.build();
		
		String jsonStr = new Gson().toJson(message);
		
		// when
		mockMvc.perform(RestDocumentationRequestBuilders.post("/messages/response")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
		.andDo(print())
		// then
		.andExpect(status().isOk())
		.andDo(document.document(requestFields(
				fieldWithPath("prdtId").type(JsonFieldType.STRING).description("판매중인 상품"),
				fieldWithPath("buyer").type(JsonFieldType.STRING).description("판매자"),
				fieldWithPath("seller").type(JsonFieldType.STRING).description("구매자"),
				fieldWithPath("content").type(JsonFieldType.STRING).description("메시지 내용"),
				fieldWithPath("type").type(JsonFieldType.STRING).description("판매자/구매자 구분")
				)));
	}

	public void createUserOne() throws Exception {
		// given
		UserVO user = UserVO.builder()
				.userId("changon")
				.userPwd("changon1234")
				.userName("홍길동")
				.userEmail("changon@gmail.com")
				.userPhone("01012341234")
				.userAddr("대구 광역시 동구")
				.build();
		String jsonStr = new Gson().toJson(user);
		
		// when
		mockMvc.perform(RestDocumentationRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
		// then
		.andExpect(status().isOk());
	}
	
	public void createUserTwo() throws Exception {
		// given
		UserVO user = UserVO.builder()
				.userId("bang")
				.userPwd("bang1234")
				.userName("이방원")
				.userEmail("bang@gmail.com")
				.userPhone("01002420404")
				.userAddr("대구 로스 엔젤리너스")
				.build();
		String jsonStr = new Gson().toJson(user);
		
		// when
		mockMvc.perform(RestDocumentationRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
		// then
		.andExpect(status().isOk());
	}
	
	public void createProduct() throws Exception {
		// given
		createUserOne();
		
		UserVO user = UserVO.builder()
				.userId("changon")
				.userPwd("changon1234")
				.userName("홍길동")
				.userEmail("changon@gmail.com")
				.userPhone("01012341234")
				.userAddr("대구 광역시 동구")
				.build();
		String userJson = new Gson().toJson(user);
		String productJson = new Gson().toJson(product);
		MockMultipartFile imageFile = new MockMultipartFile("productImage", "CDPlayer.jpeg", "image/jpeg", "<<jpeg data>>".getBytes(StandardCharsets.UTF_8));
		MockMultipartFile userFile = new MockMultipartFile("user", "user", "application/json", userJson.getBytes(StandardCharsets.UTF_8));
		MockMultipartFile productFile = new MockMultipartFile("product", "product", "application/json", productJson.getBytes(StandardCharsets.UTF_8));
		
		// when
		mockMvc.perform(MockMvcRequestBuilders.multipart("/products/new")
				.file(imageFile)
				.file(userFile)
				.file(productFile)
				)
		// then
		.andExpect(status().isOk());
	}
}
