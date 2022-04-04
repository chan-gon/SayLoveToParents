package com.board.mapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.UserVO;

/**
 * User 데이터를 다루는 쿼리가 제대로 작성 되었는지 / 주어진 환경에서 제대로 동작하는지 확인.
 * 
 * 테스트 네이밍 규칙 : [SQL 작업 내용]_[테스트 내용(Mapper 클래스 메소드 이름을 사용했음)]
 * 
 * @Transactional : 트랜잭션 테스트 후 트랜잭션을 강제 롤백해서 DB에 반영되지 않도록 한다.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Transactional
public class UserMapperTest {

	private final static String TEST_VALUE = "testVal";

	@Autowired
	private UserMapper userMapper;

	private UserVO user;

	@Before
	public void setUpUser() {
		user = UserVO.builder()
				.userId(TEST_VALUE)
				.userPwd(TEST_VALUE)
				.userName(TEST_VALUE)
				.userEmail("testVal@gmail.com")
				.userPhone("01012341234")
				.userAddr(TEST_VALUE)
				.build();
	}
	
	public void createUser() {
		userMapper.signUpUser(user);
	}

	@Test
	public void Successfully_create_user() {
		// given
		// 상단의 setUpUser()
		
		// when
		userMapper.signUpUser(user);
		
		// then
		assertThat(userMapper.getUserById(TEST_VALUE), is(not(nullValue())));
	}

	@Test
	public void This_id_is_already_existed() {
		// given
		createUser();
		
		// when, then
		assertEquals("EXISTED", userMapper.isExistUserId(user.getUserId()));
	}
	
	@Test
	public void This_email_is_already_existed() {
		// given
		createUser();
		
		// when, then
		assertEquals("EXISTED", userMapper.isExistUserEmail(user.getUserEmail()));
	}

	@Test
	public void Successfully_retrieve_exist_user() {
		// given
		createUser();
		
		// when
		UserVO existUser = userMapper.getUserById(user.getUserId());
		
		// then
		assertThat(existUser, is(not(nullValue())));
	}

	@Test
	public void Successfully_retrieve_user_id() {
		// given
		createUser();
		
		// when
		String userId = userMapper.getIdByNameAndPhone(user);
		
		// then
		assertThat(user.getUserId(), is(userId));
	}

	@Test
	public void select_getAccountId() {
		// given
		createUser();
		
		// when
		UserVO insertUser = userMapper.getUserById(user.getUserId());
		
		// then
		assertEquals(insertUser.getAccountId(), userMapper.getAccountId(TEST_VALUE));
	}

	@Test
	public void select_isValidIdAndEmailVALID() {
		// given
		createUser();
		
		// when
		String result = userMapper.isValidIdAndEmail(user.getUserId(), user.getUserEmail());
		
		// then
		assertThat(result, is("VALID"));
	}

	@Test
	public void select_isValidIdAndEmailINVALID() {
		// given
		// 상단의 setUpUser()
		
		// when
		String result = userMapper.isValidIdAndEmail(user.getUserId(), user.getUserEmail());
		
		// then
		assertThat(result, is("INVALID"));
	}

	@Test
	public void update_changeUserPwd() {
		// given
		createUser();
		// 삽입한 유저 정보 출력
		UserVO insertUser = userMapper.getUserById(user.getUserId());
		// 유저 정보 수정
		UserVO updateUser = UserVO.builder()
				.accountId(insertUser.getAccountId())
				.userPwd("newPwd")
				.build();
		
		// when
		userMapper.changeUserPwd(updateUser);
		
		// then
		// 유저 정보 수정 확인
		assertEquals("newPwd", userMapper.getUserPwd(user.getUserId()));
	}

	@Test
	public void update_changeUserProfile() {
		// given
		createUser();
		// 삽입한 유저 정보 출력
		UserVO insertUser = userMapper.getUserById(user.getUserId());
		// 유저 정보 수정
		UserVO updateUser = UserVO.builder()
				.accountId(insertUser.getAccountId())
				.userEmail("newEmail@gmail.com")
				.userPhone("01077777777")
				.userAddr("newAddr")
				.build();
		
		// when
		userMapper.changeUserProfile(updateUser);
		
		// then
		// 유저 정보 수정 확인
		assertEquals("newEmail@gmail.com", userMapper.getUserById(user.getUserId()).getUserEmail());
		assertEquals("01077777777", userMapper.getUserById(user.getUserId()).getUserPhone());
		assertEquals("newAddr", userMapper.getUserById(user.getUserId()).getUserAddr());
	}

}
