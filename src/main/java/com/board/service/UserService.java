package com.board.service;

import com.board.domain.UserVO;

public interface UserService {
	
	UserVO getUserById(String userId);

	void signUpUser(UserVO user);

	void isExistUserId(String userId);

	void isExistUserEmail(String userEmail);

	void deleteUser(String userPwdm, UserVO user);

	String getIdByNameAndPhone(String userName, String userPhone);

	void checkUserIdEmail(String userId, String userEmail);
	
	void changeUserPwd(UserVO user);
	
	void changeUserProfile(UserVO user);
}
