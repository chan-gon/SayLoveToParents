package com.board.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.board.domain.UserVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.board.config.RootConfig.class})
@Log4j
public class UserServiceTest {

	@Setter(onMethod_ = {@Autowired})
	private UserService service;
	
	@Test
	public void 사용자_생성_테스트() {
		service.deleteUser("test");
		
		UserVO user = new UserVO();
		user.setUserId("test");
		user.setUserPwd("1234");
		user.setUserName("김토마토");
		user.setUserEmail("tomato@naver.com");
		user.setUserPhone("010-7979-7979");
		user.setUserAddr("서울시 동대문구");

		service.signUpUser(user);

		log.info(user);
	}
	
	@Test
	public void 아이디_중복_확인_테스트() {
		assertTrue(service.isExistUserId("test"));
	}

}
