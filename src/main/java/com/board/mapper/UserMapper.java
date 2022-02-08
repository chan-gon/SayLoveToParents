package com.board.mapper;

import com.board.domain.UserVO;

public interface UserMapper {
	
	void signUpUser(UserVO user);
	
	int isExistUserId(String userId);
	
	int deleteUser(String userId);

}
