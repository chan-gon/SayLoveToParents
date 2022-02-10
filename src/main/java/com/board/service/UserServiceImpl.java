package com.board.service;

import org.springframework.stereotype.Service;

import com.board.domain.UserVO;
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
	public boolean isExistUserId(String userId) {
		int cnt = mapper.isExistUserId(userId);
		return cnt > 0;
	}

	@Override
	public boolean isExistUserEmail(String userEmail) {
		int cnt = mapper.isExistUserEmail(userEmail);
		return cnt > 0;
	}

	@Override
	public boolean deleteUser(String userId) {
		return mapper.deleteUser(userId) == 1;
	}

}
