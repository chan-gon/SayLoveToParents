package com.board.service;

import org.springframework.stereotype.Service;

import com.board.domain.UserVO;
import com.board.exception.EmailAlreadyExistsException;
import com.board.exception.InvalidValueException;
import com.board.exception.UserAlreadyExistsException;
import com.board.mapper.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserMapper mapper;

	@Override
	public void signUpUser(UserVO user) {
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
	public String findUserId(UserVO user) {
		// TODO Auto-generated method stub
		return null;
	}

}
