package com.board.mapper;

import org.apache.ibatis.annotations.Param;

import com.board.domain.UserVO;

public interface UserMapper {

	UserVO getUserById(String userId);
	
	void signUpUser(UserVO user);

	String isExistUserId(String userId);

	String isExistUserEmail(String userEmail);

	String getIdByNameAndPhone(UserVO user);
	
	String getAccountId(String userId);
	
	String isValidIdAndEmail(@Param("userId") String userId, @Param("userEmail") String userEmail);
	
	void changeUserPwd(UserVO user);
	
	String getUserPwd(String userId);
	
	void changeUserProfile(UserVO user);

	/*
	 * 회원 탈퇴 관련
	 */
	void deleteUserPermanent(@Param("userId") String userId, @Param("userEmail") String userEmail);
}
