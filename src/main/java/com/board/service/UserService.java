package com.board.service;

import com.board.domain.UserVO;

public interface UserService {
	
	UserVO getUserById(String accountId);

	void signUpUser(UserVO user);

	void isExistUserId(String userId);

	void isExistUserEmail(String userEmail);

	void isValidIdAndEmail(String userId, String userEmail);

	void deleteUser(String userPwdm, UserVO user);

	String getIdByNameAndPhone(UserVO user);
	
	void changeUserPwd(UserVO user);
	
	void changeUserProfile(UserVO user);
}
