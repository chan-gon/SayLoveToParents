package com.board.service;

import org.springframework.stereotype.Service;

import com.board.domain.UserVO;
import com.board.exception.EmailAlreadyExistsException;
import com.board.exception.InvalidValueException;
import com.board.exception.UserAlreadyExistsException;
import com.board.mapper.UserMapper;
import com.board.utils.PasswordEncryptor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserMapper mapper;
	
	@Override
	public void signUpUser(UserVO user) {
		String encodedPwd = PasswordEncryptor.encrypt(user.getUserPwd());
		
		UserVO encryptedUser = UserVO.builder()
				.userId(user.getUserId())
				.userPwd(encodedPwd)
				.userName(user.getUserName())
				.userEmail(user.getUserEmail())
				.userPhone(user.getUserPhone())
				.userAddr(user.getUserAddr())
				.build();
		
		mapper.signUpUser(encryptedUser);
	}

	@Override
	public void isExistUserId(String userId) {
		int cnt = mapper.isExistUserId(userId);
		if (cnt > 0) {
			throw new UserAlreadyExistsException(String.format("(%s)는 이미 존재하는 아이디입니다.", userId));
		}
	}

	@Override
	public void isExistUserEmail(String userEmail) {
		int cnt = mapper.isExistUserEmail(userEmail);
		if (cnt > 0) {
			throw new EmailAlreadyExistsException(String.format("(%s)는 이미 존재하는 이메일입니다.", userEmail));
		}
	}

	@Override
	public void deleteUser(String inputPwd, UserVO currentUser) {
		
		boolean isValidPassword = PasswordEncryptor.isMatch(inputPwd, currentUser.getUserPwd());
		System.out.println("delete = " + isValidPassword);
		if (!isValidPassword) {
			throw new InvalidValueException("올바르지 않은 값입니다. 다시 입력해주세요.");
		}
		
		mapper.deleteUser(currentUser.getUserId());
	}

	@Override
	public String getIdByNameAndPhone(String userName, String userPhone) {
		if (userName == null || userPhone == null) {
			throw new InvalidValueException("올바르지 않은 값입니다. 다시 입력해주세요.");
		}
		return mapper.getIdByNameAndPhone(userName, userPhone);
	}

	/*
	 * Controller의 /help/pwd/email 핸들러 전용 함수.
	 * 기존의 isExistUserId, isExistUserEmail 함수로 구현 가능하지만,
	 * 해당 함수들은 기존 로직과 연결되어 있어 함수간 관계 파악 후 리팩토링 예정
	 */
	@Override
	public void checkUserIdEmail(String userId, String userEmail) {
		int cnt = mapper.checkUserIdEmail(userId, userEmail);
		if (cnt == 0) {
			throw new InvalidValueException("올바르지 않은 값입니다. 다시 입력해주세요.");
		}
	}

	@Override
	public void changeUserPwd(UserVO user) {
		
		String encodedPwd = PasswordEncryptor.encrypt(user.getUserPwd());

		UserVO encryptedUser = UserVO.builder()
				.userId(user.getUserId())
				.userPwd(encodedPwd)
				.build();
		
		mapper.changeUserPwd(encryptedUser);
	}

	@Override
	public void changeUserProfile(UserVO user) {
		mapper.changeUserProfile(user);
	}

	@Override
	public UserVO getUserById(String userId) {
		return mapper.getUserById(userId);
	}

}
