package com.board.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class ProductControllerTest {
	
	@Rule 
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
	
	@Autowired
	private ProductController productController;
	
	@Autowired
	private ProductService productService;
	
	@Autowired 
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	private RestDocumentationResultHandler document;
	
	private UserVO user;
	
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
		this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	
	@Before
	public void setUpUser() {
		user = UserVO.builder()
				.userId("changon")
				.userPwd("changon1234")
				.userName("홍길동")
				.userEmail("changon@gmail.com")
				.userPhone("01012341234")
				.userAddr("대구 광역시 동구")
				.build();
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
	
	public void createUserOne() throws Exception {
		String jsonStr = new Gson().toJson(user);
		mockMvc.perform(RestDocumentationRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
		.andExpect(status().isOk());
	}
	
	public void createUserTwo() throws Exception {
		user = UserVO.builder()
				.userId("bang")
				.userPwd("bang1234")
				.userName("이방원")
				.userEmail("bang@gmail.com")
				.userPhone("01002420404")
				.userAddr("대구 로스 엔젤리너스")
				.build();
		String jsonStr = new Gson().toJson(user);
		mockMvc.perform(RestDocumentationRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
		.andExpect(status().isOk());
	}
	
	@Test
	public void createProduct() throws Exception {
		// given
		createUserOne();
		
		user = UserVO.builder()
				.userId("changon")
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
		.andDo(print())
		// then
		.andExpect(status().isOk())
		.andDo(document.document(
				requestParts(
						partWithName("productImage").description("등록 상품 이미지"),
						partWithName("user").description("회원 정보"),
						partWithName("product").description("등록 상품 정보")
						),
				requestPartFields(
						"user", 
						fieldWithPath("userId").type(JsonFieldType.STRING).description("회원 아이디")
						),
				requestPartFields(
						"product", 
						fieldWithPath("prdtName").type(JsonFieldType.STRING).description("상품 이름"),
						fieldWithPath("prdtPrice").type(JsonFieldType.STRING).description("상품 가격"),
						fieldWithPath("prdtCategory").type(JsonFieldType.STRING).description("상품 카테고리"),
						fieldWithPath("prdtInfo").type(JsonFieldType.STRING).description("상품 정보"),
						fieldWithPath("prdtCondition").type(JsonFieldType.STRING).description("상품 상태"),
						fieldWithPath("prdtIsTradeable").type(JsonFieldType.STRING).description("교환여부"),
						fieldWithPath("prdtIsDeliveryFree").type(JsonFieldType.STRING).description("배송비 여부"),
						fieldWithPath("prdtLikeCnt").type(JsonFieldType.NUMBER).description("상품 좋아요 숫자").optional(),
						fieldWithPath("prdtTradeLoc").type(JsonFieldType.STRING).description("거래 장소")
						)));
	}
	
	@Test
	@WithMockUser(username = "bang", password = "bang1234", authorities = {"ROLE_USER"})
	public void likeProduct() throws Exception {
		// given
		// 회원1(changon) 생성 및 상품 등록
		createProduct();
		// 회원2(bang) 생성
		createUserTwo();
		
		// 등록한 상품 정보 호출
		ProductVO insertProduct = productService.getProductIdByName(product.getPrdtName());
		
		// when
		mockMvc.perform(RestDocumentationRequestBuilders.post("/products/like/{prdtId}", insertProduct.getPrdtId()))
		// then
		.andExpect(status().isOk())
		.andDo(document.document(pathParameters(
				parameterWithName("prdtId").description("상품 아이디")
				)));
	}
	
	@Test
	@WithMockUser(username = "bang", password = "bang1234", authorities = {"ROLE_USER"})
	public void unlikeProduct() throws Exception {
		// given
		// 사용자1 생성 및 상품 등록
		createProduct();
		// 사용자2 생성
		createUserTwo();
		// 등록한 상품 정보 호출
		ProductVO insertProduct = productService.getProductIdByName(product.getPrdtName());
		
		// when
		// 상품 좋아요 카운트 +1
		mockMvc.perform(post("/products/like/{prdtId}", insertProduct.getPrdtId()))
		.andExpect(status().isOk());
		
		// 상품 좋아요 카운트 -1
		mockMvc.perform(RestDocumentationRequestBuilders.post("/products/unlike/{prdtId}", insertProduct.getPrdtId()))
		// then
		.andExpect(status().isOk())
		.andDo(document.document(pathParameters(
				parameterWithName("prdtId").description("상품 아이디")
				)));
	}
	
	@Test
	@WithMockUser(username = "changon", password = "changon1234", authorities = {"ROLE_USER"})
	public void updateProduct() throws Exception {
		// given
		// 사용자1 생성 및 상품 등록
		createProduct();
		// 사용자2 생성
		createUserTwo();
		
		user = UserVO.builder()
				.userId("changon")
				.build();
		
		// 등록한 상품 정보 호출
		ProductVO insertProduct = productService.getProductIdByName(product.getPrdtName());
		
		// 수정된 상품 정보
		product = ProductVO.builder()
				.prdtId(insertProduct.getPrdtId())
				.prdtName("CD플레이어")
				.prdtPrice("789")
				.prdtCategory("디지털")
				.prdtInfo("aiwa CD 플레이어 팔아요.")
				.prdtCondition("새상품")
				.prdtIsTradeable("교환불가")
				.prdtIsDeliveryFree("배송비포함")
				.prdtLikeCnt(0)
				.prdtTradeLoc("대구 북구")
				.build();
		
		
		String productJson = new Gson().toJson(product);
		MockMultipartFile imageFile = new MockMultipartFile("productImage", "CDPlayer.jpeg", "image/jpeg", "<<jpeg data>>".getBytes(StandardCharsets.UTF_8));
		MockMultipartFile productFile = new MockMultipartFile("product", "product", "application/json", productJson.getBytes(StandardCharsets.UTF_8));
		
		// when
		mockMvc.perform(MockMvcRequestBuilders.multipart("/products/update/{prdtId}", insertProduct.getPrdtId())
				.file(imageFile)
				.file(productFile)
				.characterEncoding("UTF-8")
				)
		// then
		.andExpect(status().isOk())
		.andDo(document.document(
				requestParts(
						partWithName("productImage").description("등록 상품 이미지"),
						partWithName("product").description("등록 상품 정보")
						),
				requestPartFields(
						"product", 
						fieldWithPath("prdtId").type(JsonFieldType.STRING).description("상품 아이디"),
						fieldWithPath("prdtName").type(JsonFieldType.STRING).description("상품 이름"),
						fieldWithPath("prdtPrice").type(JsonFieldType.STRING).description("상품 가격"),
						fieldWithPath("prdtCategory").type(JsonFieldType.STRING).description("상품 카테고리"),
						fieldWithPath("prdtInfo").type(JsonFieldType.STRING).description("상품 정보"),
						fieldWithPath("prdtCondition").type(JsonFieldType.STRING).description("상품 상태"),
						fieldWithPath("prdtIsTradeable").type(JsonFieldType.STRING).description("교환여부"),
						fieldWithPath("prdtIsDeliveryFree").type(JsonFieldType.STRING).description("배송비 여부"),
						fieldWithPath("prdtLikeCnt").type(JsonFieldType.NUMBER).description("상품 좋아요 숫자").optional(),
						fieldWithPath("prdtTradeLoc").type(JsonFieldType.STRING).description("거래 장소")
						)));
	}
	
	@Test
	@WithMockUser(username = "bang", password = "bang1234", authorities = {"ROLE_USER"})
	public void deleteProduct() throws Exception {
		// given
		// 사용자1 생성 및 상품 등록
		createProduct();
		// 사용자2 생성
		createUserTwo();
		// 등록한 상품 정보 호출
		ProductVO insertProduct = productService.getProductIdByName(product.getPrdtName());
		
		// when
		mockMvc.perform(RestDocumentationRequestBuilders.post("/products/delete/{prdtId}", insertProduct.getPrdtId()))
		// then
		.andExpect(status().isOk())
		.andDo(document.document(pathParameters(
				parameterWithName("prdtId").description("상품 아이디")
				)));
	}
}
