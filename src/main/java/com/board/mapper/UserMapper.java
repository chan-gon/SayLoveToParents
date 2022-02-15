package com.board.mapper;

import com.board.domain.UserVO;

public interface UserMapper {

	UserVO getUserInfoById(String userId);

	void signUpUser(UserVO user);

	int isExistUserId(String userId);

	int isExistUserEmail(String userEmail);

	int deleteUser(String userId);

	String findUserId(String userName);

}
