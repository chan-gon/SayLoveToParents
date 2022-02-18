package com.board.service;

import com.board.domain.UserVO;

public interface UserService {

	void signUpUser(UserVO user);

	void isExistUserId(String userId);

	void isExistUserEmail(String userEmail);

	void deleteUser(String userId);

	String findUserId(String userName, String userPhone);

	String findUserPwd(String userName, String userPhone);
	
	int checkUserIdEmail(String userId, String userEmail);
	
	void changeUserPwd(UserVO user);
	
	UserVO selectByUserId(String userId);
	
	void changeUserProfile(UserVO user);
}
