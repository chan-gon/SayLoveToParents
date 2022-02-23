package com.board.service;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Test.None;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.board.domain.UserVO;
import com.board.exception.EmailAlreadyExistsException;
import com.board.exception.InvalidValueException;
import com.board.exception.UserAlreadyExistsException;
import com.board.utils.PasswordEncryptor;

import lombok.Setter;

/*
 * @RunWith(SpringJUnit4ClassRunner.class)
 * : ApplicationContext를 만들고 관리하는 작업을 할 수 있도록 jUnit의 기능을 확장한다.
 *   즉 컨테이너 객체를 생성해서 테스트에 사용할 수 있도록 해준다.
 *   원래 junit에서는 테스트 메소드별로 객체를 따로 생성해서 관리했는데, Spring-Test 라이브러리로 
 *   확장된 junit에서는 컨테이터를 통해 싱글톤으로 관리되는 객체를 사용해서 모든 테스트에 사용한다.
 *   
 * @ContextConfiguration
 * : 스프링 빈 설정 파일의 위치를 지정한다.
 *   @RunWith 어노테이션으로 생성하는 컨테이너가 참고하는 빈 설정 파일이라고 생각하면 된다.
 *   
 * @FixMethodOrder
 * : 테스트 메소드 실행 순서를 관리할 수 있도록 해주는 어노테이션.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest {
	
	private static final String FAKE_ID = "anonymous";
	private static final String FAKE_EMAIL = "anonymous@naver.com";
	private static final String DEFAULT_PWD = "test";
	
	@Setter(onMethod_ = {@Autowired})
	private UserService service;
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	public UserVO testUser;

	/*
	 * @Before
	 * : 각각의 테스트 실행 이전에 먼저 실행된다.
	 */
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
	
	@Test(expected = None.class)
	public void a_사용자_생성_테스트() {
		service.signUpUser(testUser);
	}
	
	@Test(expected = None.class)
	public void b_아이디_중복_확인_테스트_실패() {
		service.isExistUserId(FAKE_ID);
	}
	
	@Test
	public void c_아이디_중복_확인_테스트_성공() {
		exceptionRule.expect(UserAlreadyExistsException.class);
		exceptionRule.expectMessage("(" + testUser.getUserId() + ")는 이미 존재하는 아이디입니다.");
		service.isExistUserId(testUser.getUserId());
	}
	
	@Test(expected = None.class)
	public void d_이메일_중복_확인_테스트_실패() {
		service.isExistUserEmail(FAKE_EMAIL);
	}
	
	@Test
	public void e_이메일_중복_확인_테스트_성공() {
		exceptionRule.expect(EmailAlreadyExistsException.class);
		exceptionRule.expectMessage("(" + testUser.getUserEmail() + ")는 이미 존재하는 이메일입니다.");
		service.isExistUserEmail(testUser.getUserEmail());
	}
	
	@Test
	public void f_아이디_찾기_테스트() {
		String id = service.getIdByNameAndPhone(testUser.getUserName(), testUser.getUserPhone());
		assertTrue(id.equals(testUser.getUserId()));
	}
	
	@Test(expected = None.class)
	public void g_비밀번호_변경_테스트() {
		UserVO user = UserVO.builder()
				.userId(testUser.getUserId())
				.userPwd("newPwd")
				.build();
		service.changeUserPwd(user);
	}
	
	@Test
	public void h_회원_탈퇴_테스트_실패() {
		
		UserVO deleteUser = service.getUserById(testUser.getUserId());
		
		exceptionRule.expect(InvalidValueException.class);
		exceptionRule.expectMessage("올바르지 않은 값입니다. 다시 입력해주세요.");
		service.deleteUser("wrongPwd", deleteUser);
	}
	
	@Test(expected = None.class)
	public void i_회원_탈퇴_테스트_성공() {
		service.deleteUser(DEFAULT_PWD, testUser);
	}
	
}
