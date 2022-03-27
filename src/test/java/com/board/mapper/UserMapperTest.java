package com.board.mapper;

import static org.junit.Assert.assertEquals;

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
	public void setUp() {
		user = UserVO.builder()
				.userId(TEST_VALUE)
				.userPwd(TEST_VALUE)
				.userName(TEST_VALUE)
				.userEmail("testVal@gmail.com")
				.userPhone("01012341234")
				.userAddr(TEST_VALUE)
				.build();
	}

	@Test
	public void insert_signUpUser() {
		userMapper.signUpUser(user);
	}

	@Test
	public void select_isExistUserIdEXISTED() {
		userMapper.signUpUser(user);
		assertEquals("EXISTED", userMapper.isExistUserId(user.getUserId()));
	}

	@Test
	public void select_isExistUserIdNOT_EXISTED() {
		assertEquals("NOT_EXISTED", userMapper.isExistUserId(user.getUserId()));
	}

	@Test
	public void select_getUserById() {
		userMapper.signUpUser(user);
		userMapper.getUserById(user.getUserId());
	}

	@Test
	public void select_getIdByNameAndPhone() {
		userMapper.signUpUser(user);
		assertEquals(TEST_VALUE, userMapper.getIdByNameAndPhone(user));
	}

	@Test
	public void select_getAccountId() {
		userMapper.signUpUser(user);
		UserVO insertUser = userMapper.getUserById(user.getUserId());
		assertEquals(insertUser.getAccountId(), userMapper.getAccountId(TEST_VALUE));
	}

	@Test
	public void select_isValidIdAndEmailVALID() {
		userMapper.signUpUser(user);
		assertEquals("VALID", userMapper.isValidIdAndEmail(user.getUserId(), user.getUserEmail()));
	}

	@Test
	public void select_isValidIdAndEmailINVALID() {
		assertEquals("INVALID", userMapper.isValidIdAndEmail(user.getUserId(), user.getUserEmail()));
	}

	@Test
	public void update_changeUserPwd() {
		userMapper.signUpUser(user);
		// 삽입한 유저 정보 출력
		UserVO insertUser = userMapper.getUserById(user.getUserId());
		// 유저 정보 수정
		UserVO updateUser = UserVO.builder()
				.accountId(insertUser.getAccountId())
				.userPwd("newPwd")
				.build();
		userMapper.changeUserPwd(updateUser);
		// 유저 정보 수정 확인
		assertEquals("newPwd", userMapper.getUserPwd(user.getUserId()));
	}

	@Test
	public void update_changeUserProfile() {
		userMapper.signUpUser(user);
		// 삽입한 유저 정보 출력
		UserVO insertUser = userMapper.getUserById(user.getUserId());
		// 유저 정보 수정
		UserVO updateUser = UserVO.builder()
				.accountId(insertUser.getAccountId())
				.userEmail("newEmail@gmail.com")
				.userPhone("01077777777")
				.userAddr("newAddr")
				.build();
		userMapper.changeUserProfile(updateUser);
		// 유저 정보 수정 확인
		assertEquals("newEmail@gmail.com", userMapper.getUserById(user.getUserId()).getUserEmail());
		assertEquals("01077777777", userMapper.getUserById(user.getUserId()).getUserPhone());
		assertEquals("newAddr", userMapper.getUserById(user.getUserId()).getUserAddr());
	}

}
