package com.board.mapper;

import org.apache.ibatis.annotations.Param;

import com.board.domain.UserVO;

public interface UserMapper {

	UserVO getUserById(String userId);
	
	String getPassword(String userId);
	
	void signUpUser(UserVO user);

	int isExistUserId(String userId);

	int isExistUserEmail(String userEmail);

	String getIdByNameAndPhone(@Param("userName") String userName, @Param("userPhone") String userPhone);
	
	int checkUserIdEmail(@Param("userId") String userId, @Param("userEmail") String userEmail);
	
	void changeUserPwd(UserVO user);
	
	void changeUserProfile(UserVO user);

	void deleteUser(String userId);
}
