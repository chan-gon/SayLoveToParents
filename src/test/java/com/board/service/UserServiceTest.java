package com.board.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Test.None;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.board.domain.UserVO;
import com.board.exception.EmailAlreadyExistsException;
import com.board.exception.InvalidValueException;
import com.board.exception.UserAlreadyExistsException;
import com.board.exception.UserNotExistsException;
import com.board.util.PasswordEncryptor;

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
public class UserServiceTest {
	
	private static final String TEST_ACNT_ID = "1111";
	private static final String TEST_PWD = "test";
	private static final String NOT_EXIST_ID = "none";
	private static final String NOT_EXIST_EMAIL = "none@naver.com";
	
	@Autowired
	private UserService userService;
	
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
				.accountId(TEST_ACNT_ID)
				.userId("test")
				.userPwd(PasswordEncryptor.encrypt(TEST_PWD))
				.userName("test")
				.userEmail("test@gmail.com")
				.userPhone("01077777777")
				.userAddr("미국 조지아")
				.build();
		
	}
	
	@Test(expected = None.class)
	public void 회원가입_테스트_성공() {
		userService.signUpUser(testUser);
	}
	
	@Test
	public void 회원가입_테스트_실패() {
		exceptionRule.expect(UserAlreadyExistsException.class);
		exceptionRule.expectMessage("사용자 중복 에러. 동일한 값을 가진 사용자가 존재합니다.");
		userService.signUpUser(testUser);
	}
	
	/*
	 * 중복되는 아이디가 존재하지 않으면 성공
	 */
	@Test(expected = None.class)
	public void 아이디_중복_확인_테스트_실패() {
		userService.isExistUserId(NOT_EXIST_ID);
	}
	
	/*
	 * 중복되는 아이디가 존재하면 성공
	 */
	@Test
	public void 아이디_중복_확인_테스트_성공() {
		exceptionRule.expect(UserAlreadyExistsException.class);
		exceptionRule.expectMessage("(" + testUser.getUserId() + ")는 이미 존재하는 아이디입니다.");
		userService.isExistUserId(testUser.getUserId());
	}
	
	@Test(expected = None.class)
	public void 이메일_중복_확인_테스트_실패() {
		userService.isExistUserEmail(NOT_EXIST_EMAIL);
	}
	
	@Test
	public void 이메일_중복_확인_테스트_성공() {
		exceptionRule.expect(EmailAlreadyExistsException.class);
		exceptionRule.expectMessage("(" + testUser.getUserEmail() + ")는 이미 존재하는 이메일입니다.");
		userService.isExistUserEmail(testUser.getUserEmail());
	}
	
	@Test
	public void 아이디_찾기_테스트_실패() {
		exceptionRule.expect(InvalidValueException.class);
		exceptionRule.expectMessage("올바르지 않은 값입니다. 다시 입력해주세요.");
		String id = userService.getIdByNameAndPhone(null);
		assertNull(id);
	}
	
	@Test(expected = None.class)
	public void 아이디_찾기_테스트_성공() {
		String id = userService.getIdByNameAndPhone(testUser);
		assertTrue(id.equals("test"));
	}
	
	@Test
	public void 비밀번호_변경_테스트_실패() {
		exceptionRule.expect(UserNotExistsException.class);
		exceptionRule.expectMessage("존재하지 않는 사용자입니다.");
		UserVO user = UserVO.builder()
				.accountId("none")
				.userId(NOT_EXIST_ID)
				.userPwd("newPwd")
				.build();
		userService.changeUserPwd(user);
	}
	
	/*
	 * 이메일, 전화번호, 주소 입력값 유효성 검증 로직 필요
	 */
	@Test
	public void 프로필_수정_성공() {
		// 이메일 수정
		UserVO editUser1 = UserVO.builder()
				.accountId(TEST_ACNT_ID)
				.userEmail("edit")
				.build();
		userService.changeUserProfile(editUser1);
		
		// 전화번호 수정
		UserVO editUser2 = UserVO.builder()
				.accountId(TEST_ACNT_ID)
				.userPhone("edit")
				.build();
		userService.changeUserProfile(editUser2);
		
		// 주소 수정
		UserVO editUser3 = UserVO.builder()
				.accountId(TEST_ACNT_ID)
				.userAddr("edit")
				.build();
		userService.changeUserProfile(editUser3);
	}
	
	@Test(expected = None.class)
	public void 비밀번호_변경_테스트_성공() {
		UserVO user = UserVO.builder()
				.userId(testUser.getUserId())
				.userPwd("newPwd")
				.build();
		userService.changeUserPwd(user);
	}
	
	@Test(expected = None.class)
	public void 회원_탈퇴_테스트() {
		userService.deleteUser(TEST_PWD, testUser);
	}
	
}
