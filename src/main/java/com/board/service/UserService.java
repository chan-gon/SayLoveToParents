package com.board.service;

import com.board.domain.UserVO;

public interface UserService {

	void signUpUser(UserVO user);

	void isExistUserId(String userId);
	
	void isExistUserEmail(String userEmail);

	void deleteUser(String userId);
	
	String findUserId(String userName);
}
