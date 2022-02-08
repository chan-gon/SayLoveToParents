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
	public int isExistUserId(String userId) {
		int cnt = mapper.isExistUserId(userId);
		return cnt;
	}

	@Override
	public void deleteUser(String userId) {
		mapper.deleteUser(userId);
	}

}
