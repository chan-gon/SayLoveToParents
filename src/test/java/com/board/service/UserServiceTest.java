package com.board.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.UserVO;
import com.board.exception.user.UserNotFoundException;

/**
 * * @RunWith(SpringJUnit4ClassRunner.class) 
 * : ApplicationContext를 만들고 관리하는 작업을 할 수 있도록 jUnit의 기능을 확장한다. 
 * 즉 컨테이너 객체를 생성해서 테스트에 사용할 수 있도록 해준다. 원래 junit에서는 테스트
 * 메소드별로 객체를 따로 생성해서 관리했는데, Spring-Test 라이브러리로 확장된 junit에서는 컨테이터를 통해 싱글톤으로 관리되는
 * 객체를 사용해서 모든 테스트에 사용한다.
 * 
 * @ContextConfiguration : 스프링 빈 설정 파일의 위치를 지정한다.
 * 
 * 네이밍 규칙
 * - 특정 네이밍 규칙에 얽매이지 말 것.(가독성을 떨어뜨리는 원인이 된다)
 * - 비개발자도 쉽게 이해할 수 있도록 작성.
 * - 단어와 단어 사이는 언더스코어(_)로 구분.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Transactional
public class UserServiceTest {
	private final static String TEST_VALUE = "testVal";

	@Autowired
	private UserService userService;
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	private UserVO user;
	
	public void createUser() {
		userService.signUpUser(user);
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
	public void Successfully_created_new_user() {
		userService.signUpUser(user);
	}
	
	@Test
	public void Cannot_use_this_id() {
		createUser();
		exceptionRule.expect(IllegalArgumentException.class);
		userService.isExistUserId(user.getUserId());
	}
	
	@Test
	public void Can_use_this_id() {
		userService.isExistUserId(user.getUserId());
	}
	
	@Test
	public void Cannot_use_this_email() {
		createUser();
		exceptionRule.expect(IllegalArgumentException.class);
		userService.isExistUserEmail(user.getUserEmail());
	}
	
	@Test
	public void Can_use_this_email() {
		userService.isExistUserEmail(user.getUserEmail());
	}
	
	@Test
	public void Email_and_id_are_valid() {
		createUser();
		userService.isValidIdAndEmail(user.getUserId(), user.getUserEmail());
	}
	
	@Test
	public void Email_and_id_are_invalid() {
		exceptionRule.expect(UserNotFoundException.class);
		userService.isValidIdAndEmail(user.getUserId(), user.getUserEmail());
	}
	
	@Test
	public void Be_able_to_retrieve_id() {
		createUser();
		assertThat(userService.getIdByNameAndPhone(user), is(TEST_VALUE));
	}
	
	@Test
	public void Not_able_to_retrieve_id() {
		exceptionRule.expect(IllegalArgumentException.class);
		userService.getIdByNameAndPhone(user);
	}
	
	@Test
	public void Successfully_change_user_password() {
		createUser();
		String oldPwd = user.getPassword();
		String newPwd = "newPwd";
		user = UserVO.builder()
				.accountId(user.getAccountId())
				.userId(TEST_VALUE)
				.userPwd(newPwd)
				.build();
		userService.changeUserPwd(user);
		UserVO updatedUser = userService.getUserById(user.getUserId());
		assertThat(updatedUser.getPassword(), is(not(oldPwd)));
	}
	
	@Test
	public void Successfully_change_user_profile() {
		String updateVal = "updateVal";
		createUser();
		user = UserVO.builder()
				.accountId(user.getAccountId())
				.userId(TEST_VALUE)
				.userEmail(updateVal)
				.userPhone(updateVal)
				.userAddr(updateVal)
				.build();
		userService.changeUserProfile(user);
		UserVO updatedUser = userService.getUserById(user.getUserId());
		assertThat(updatedUser.getUserEmail(), is(updateVal));
		assertThat(updatedUser.getUserPhone(), is(updateVal));
		assertThat(updatedUser.getUserAddr(), is(updateVal));
	}
	
	@Test
	public void Permanently_delete_user() {
		createUser();
		userService.deleteUserPermanent(user.getUserId(), user.getUserEmail());
		assertThat(userService.getUserById(TEST_VALUE), is(nullValue()));
	}
}
