package com.board.service;

import org.springframework.stereotype.Service;

import com.board.domain.UserVO;
import com.board.exception.EmailAlreadyExistsException;
import com.board.exception.InvalidValueException;
import com.board.exception.UserAlreadyExistsException;
import com.board.mapper.UserMapper;
import com.board.utils.PasswordEncryptor;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class UserServiceImpl implements UserService {

	private UserMapper mapper;
	
	@Override
	public void signUpUser(UserVO user) {
		String encodedPwd = PasswordEncryptor.encrypt(user.getUserPwd());
		user.setUserPwd(encodedPwd);
		mapper.signUpUser(user);
	}

	@Override
	public void isExistUserId(String userId) {
		int cnt = mapper.isExistUserId(userId);
		if (cnt > 0) {
			throw new UserAlreadyExistsException(String.format("(%s)는 이미 존재하는 아이디입니다.", userId));
		}
	}

	@Override
	public void isExistUserEmail(String userEmail) {
		int cnt = mapper.isExistUserEmail(userEmail);
		if (cnt > 0) {
			throw new EmailAlreadyExistsException(String.format("(%s)는 이미 존재하는 이메일입니다.", userEmail));
		}
	}

	@Override
	public void deleteUser(String userId) {
		int cnt = mapper.deleteUser(userId);
		if (cnt < 1) {
			throw new InvalidValueException();
		}
	}

	@Override
	public String findUserId(String userName, String userPhone) {
		return mapper.findUserId(userName, userPhone);
	}

	/*
	 * Controller의 /help/pwd/email 핸들러 전용 함수.
	 * 기존의 isExistUserId, isExistUserEmail 함수로 구현 가능하지만,
	 * 해당 함수들은 기존 로직과 연결되어 있어 함수간 관계 파악 후 리팩토링 예정
	 */
	@Override
	public int checkUserIdEmail(String userId, String userEmail) {
		return mapper.checkUserIdEmail(userId, userEmail);
	}

	@Override
	public String findUserPwd(String userName, String userPhone) {
		return mapper.findUserPwd(userName, userPhone);
	}

	@Override
	public void changeUserPwd(UserVO user) {
		String encodedPwd = PasswordEncryptor.encrypt(user.getUserPwd());
		user.setUserPwd(encodedPwd);
		mapper.changeUserPwd(user);
	}

	@Override
	public UserVO selectByUserId(String userId) {
		return mapper.selectByUserId(userId);
	}

	@Override
	public void changeUserProfile(UserVO user) {
		mapper.changeUserProfile(user);
	}

}
