package com.board.mapper;

import org.apache.ibatis.annotations.Param;

import com.board.domain.UserVO;

public interface UserMapper {

	UserVO getUserInfoById(String userId);

	void signUpUser(UserVO user);

	int isExistUserId(String userId);

	int isExistUserEmail(String userEmail);

	int deleteUser(String userId);

	String findUserId(@Param("userName") String userName, @Param("userPhone") String userPhone);

	String findUserPwd(@Param("userId") String userId, @Param("userEmail") String userEmail);

	int checkUserIdEmail(@Param("userId") String userId, @Param("userEmail") String userEmail);
	
	void changeUserPwd(UserVO user);

}
