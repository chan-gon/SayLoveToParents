package com.board.service;

import com.board.domain.UserVO;

public interface UserService {

	void signUpUser(UserVO user);

	int isExistUserId(String userId);

	void deleteUser(String userId);
}
