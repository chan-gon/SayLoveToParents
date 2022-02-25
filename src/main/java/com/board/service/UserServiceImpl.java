package com.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.StoreVO;
import com.board.domain.UserVO;
import com.board.exception.EmailAlreadyExistsException;
import com.board.exception.InvalidValueException;
import com.board.exception.UserAlreadyExistsException;
import com.board.mapper.StoreMapper;
import com.board.mapper.UserMapper;
import com.board.utils.PasswordEncryptor;

import lombok.RequiredArgsConstructor;

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
public class UserServiceImpl implements UserService {
	
	private final UserMapper userMapper;
	
	private final StoreMapper storeMapper;
	
	@Override
	@Transactional
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
		
		userMapper.signUpUser(encryptedUser);
		
		StoreVO newStore = StoreVO.builder()
				.userId(user.getUserId())
				.build();
		
		storeMapper.addStore(newStore);
		
	}

	@Override
	public void isExistUserId(String userId) {
		int cnt = userMapper.isExistUserId(userId);
		if (cnt > 0) {
			throw new UserAlreadyExistsException(String.format("(%s)는 이미 존재하는 아이디입니다.", userId));
		}
	}

	@Override
	public void isExistUserEmail(String userEmail) {
		int cnt = userMapper.isExistUserEmail(userEmail);
		if (cnt > 0) {
			throw new EmailAlreadyExistsException(String.format("(%s)는 이미 존재하는 이메일입니다.", userEmail));
		}
	}

	@Override
	@Transactional
	public void deleteUser(String inputPwd, UserVO currentUser) {
		
		boolean isValidPassword = PasswordEncryptor.isMatch(inputPwd, currentUser.getUserPwd());
		System.out.println("delete = " + isValidPassword);
		if (!isValidPassword) {
			throw new InvalidValueException("올바르지 않은 값입니다. 다시 입력해주세요.");
		}
		
		storeMapper.deleteStore(currentUser.getUserId());
		userMapper.deleteUser(currentUser.getUserId());
	}

	@Override
	public String getIdByNameAndPhone(String userName, String userPhone) {
		if (userName == null || userPhone == null) {
			throw new InvalidValueException("올바르지 않은 값입니다. 다시 입력해주세요.");
		}
		return userMapper.getIdByNameAndPhone(userName, userPhone);
	}

	/*
	 * Controller의 /help/pwd/email 핸들러 전용 함수.
	 * 기존의 isExistUserId, isExistUserEmail 함수로 구현 가능하지만,
	 * 해당 함수들은 기존 로직과 연결되어 있어 함수간 관계 파악 후 리팩토링 예정
	 */
	@Override
	public void checkUserIdEmail(String userId, String userEmail) {
		int cnt = userMapper.checkUserIdEmail(userId, userEmail);
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
		
		userMapper.changeUserPwd(encryptedUser);
	}

	@Override
	public void changeUserProfile(UserVO user) {
		userMapper.changeUserProfile(user);
	}

	@Override
	public UserVO getUserById(String userId) {
		return userMapper.getUserById(userId);
	}

}
