package com.board.service;

import org.springframework.stereotype.Service;

import com.board.domain.UserVO;
import com.board.exception.user.DeleteUserException;
import com.board.exception.user.InsertUserException;
import com.board.exception.user.UpdateUserException;
import com.board.exception.user.UserExceptionMessage;
import com.board.exception.user.UserExistsException;
import com.board.exception.user.UserNotFoundException;
import com.board.mapper.UserMapper;
import com.board.util.PasswordEncryptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

/*
 * @RequiredArgsConstructor
 * : 초기화되지 않은 final 필드 또는 @NonNull이 붙은 필드를 가지는 생성자를 만든다.
 * 이를 통해 생성자 주입을 통한 동작 방식으로 구현했다. 
 * 생성자 주입을 사용하면 final 필드를 사용할 수 있어서 객체의 불변성을 확보할 수 있다. final로 선언된 필드는 반드시 선언과 동시에 초기화를 해야 하기 때문에
 * 필드 주입, 수정자 주입과 함께 사용할 수 없다.
 * 그리고 생성자는 생성에 필요한 매개변수가 잘못되면 생성 자체가 되지 않기 때문에 오류 발생시 디버깅이 편하다.
 */

@Service
@RequiredArgsConstructor
@Log4j
public class UserServiceImpl implements UserService {

	private static final String EXISTED = "EXISTED";
	private static final String INVALID = "INVALID";

	private final UserMapper userMapper;

	@Override
	public void signUpUser(UserVO user) {

		if (userMapper.isExistUserId(user.getUserId()).equals(EXISTED)) {
			throw new InsertUserException(UserExceptionMessage.INSERT_FAIL);
		}

		String encodedPwd = PasswordEncryptor.encrypt(user.getUserPwd());

		UserVO encryptedUser = UserVO.builder()
				.userId(user.getUserId())
				.userPwd(encodedPwd)
				.userName(user.getUserName())
				.userEmail(user.getUserEmail())
				.userPhone(user.getUserPhone())
				.userAddr(user.getUserAddr())
				.build();

		userMapper.signUpUser(encryptedUser);
	}

	@Override
	public void isExistUserId(String userId) {
		if (userMapper.isExistUserId(userId).equals(EXISTED)) {
			throw new UserExistsException(UserExceptionMessage.ALREADY_EXISTS);
		}
	}

	@Override
	public void isExistUserEmail(String userEmail) {
		if (userMapper.isExistUserEmail(userEmail).equals(EXISTED)) {
			throw new UserExistsException(UserExceptionMessage.ALREADY_EXISTS);
		}
	}
	
	/*
	 * 이메일 인증번호 전송을 위해
	 * 사용자 아이디/이메일 존재 여부 확인하는 메소드
	 */
	@Override
	public void isValidIdAndEmail(String userId, String userEmail) {
		if (userMapper.isValidIdAndEmail(userId, userEmail).equals(INVALID)) {
			throw new UserNotFoundException(UserExceptionMessage.NOT_FOUND);
		}
	}

	@Override
	public void deleteUser(String inputPwd, UserVO currentUser) {
		boolean isValidPassword = PasswordEncryptor.isMatch(inputPwd, currentUser.getUserPwd());
		if (!isValidPassword) {
			throw new DeleteUserException(UserExceptionMessage.DELETE_FAIL);
		}
		userMapper.deleteUser(currentUser.getUserId());
	}

	@Override
	public String getIdByNameAndPhone(UserVO user) {
		String findUserId = userMapper.getIdByNameAndPhone(user);
		if (findUserId == null) {
			throw new UserNotFoundException(UserExceptionMessage.NOT_FOUND);
		}
		return findUserId;
	}

	/**
	 * 사용자로부터 받은 아이디 값으로 accountId를 가져온 다음 유효성 체크 후 생성자를 만들어서 비밀번호 변경 메소드 매개변수로 입력
	 *
	 * 해당 메소드는 Controller 계층에서 넘어오는 UserVO 객체에 의존한다 accountId 값을 View 계층의 HTML 태그에
	 * 심어서 가져오는 방법은 보안에 취약하므로 Controller 계층의 메소드에 의존하더라도 이 방법이 안전하다고 판단했다
	 */
	@Override
	public void changeUserPwd(UserVO user) {
		try {
			UserVO newUser = userMapper.getUserById(user.getUserId());
			String encodedPwd = PasswordEncryptor.encrypt(user.getUserPwd());
			UserVO changeUser = UserVO.builder()
					.accountId(newUser.getAccountId())
					.userPwd(encodedPwd)
					.build();
			userMapper.changeUserPwd(changeUser);
		} catch (RuntimeException e) {
			throw new UpdateUserException(UserExceptionMessage.PWD_UPDATE_FAIL);
		}
	}

	@Override
	public void changeUserProfile(UserVO user) {
		try {
			String accountId = userMapper.getAccountId(user.getUserId());
			UserVO editUser = UserVO.builder()
					.accountId(accountId)
					.userEmail(user.getUserEmail())
					.userPhone(user.getUserPhone())
					.userAddr(user.getUserAddr())
					.build();
			userMapper.changeUserProfile(editUser);
		} catch (RuntimeException e) {
			throw new UpdateUserException(UserExceptionMessage.UPDATE_FAIL);
		}
	}

	@Override
	public UserVO getUserById(String userId) {
		try {
			return userMapper.getUserById(userId);
		} catch (RuntimeException e) {
			throw new UserNotFoundException(UserExceptionMessage.NOT_FOUND);
		}
	}

}
