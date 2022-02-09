package com.board.service;

import com.board.domain.UserVO;

public interface UserService {

	void signUpUser(UserVO user);

	boolean isExistUserId(String userId);
	
	boolean isExistUserEmail(String userEmail);

	boolean deleteUser(String userId);
}
