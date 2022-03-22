package com.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.ImageVO;
import com.board.domain.ProductVO;
import com.board.domain.UserVO;
import com.board.exception.user.UserExceptionMessage;
import com.board.exception.user.UserNotFoundException;
import com.board.mapper.ImageMapper;
import com.board.mapper.MessageMapper;
import com.board.mapper.ProductMapper;
import com.board.mapper.UserMapper;
import com.board.util.ImageFileUtils;
import com.board.util.PasswordEncryptor;

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

	private static final String EXISTED = "EXISTED";
	private static final String INVALID = "INVALID";

	private final UserMapper userMapper;
	
	private final ProductMapper productMapper;
	
	private final MessageMapper messageMapper;
	
	private final ImageMapper imageMapper;

	@Override
	@Transactional
	public void signUpUser(UserVO user) {
		if (userMapper.isExistUserId(user.getUserId()).equals(EXISTED) || userMapper.isExistUserEmail(user.getUserEmail()).equals(EXISTED)) {
			throw new IllegalAccessError("아이디 또는 이메일 주소가 중복되었습니다.");
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
			throw new IllegalArgumentException("이미 존재하는 아이디.");
		}
	}

	@Override
	public void isExistUserEmail(String userEmail) {
		if (userMapper.isExistUserEmail(userEmail).equals(EXISTED)) {
			throw new IllegalArgumentException("이미 존재하는 이메일.");
		}
	}
	
	/*
	 *	이메일 인증번호 전송을 위해
	 *  사용자 아이디/이메일 존재 여부 확인하는 메소드
	 */
	@Override
	public void isValidIdAndEmail(String userId, String userEmail) {
		if (userMapper.isValidIdAndEmail(userId, userEmail).equals(INVALID)) {
			throw new UserNotFoundException(UserExceptionMessage.NOT_FOUND);
		}
	}

	/**
	 *	회원 탈퇴.
	 *	DB의 모든 사용자 정보를 제거하기 위해 여러건의 삭제 작업이 필요.
	 *	좀 더 효율적인 로직을 고민할 필요가 있음.
	 */
	@Override
	@Transactional
	public void deleteUserPermanent(String userId, String userEmail) {
		if (!userMapper.isExistUserId(userId).equals(EXISTED) || !userMapper.isExistUserEmail(userEmail).equals(EXISTED)) {
			throw new IllegalArgumentException("올바른 아이디/이메일을 입력해주세요.");
		}
		String accountId = userMapper.getAccountId(userId);
		// 메시지 삭제
		messageMapper.deleteMessagesPermanent(userId);
		// 이미지 삭제
		List<ProductVO> userProductIdList = productMapper.getProductId(accountId);
		for (ProductVO product : userProductIdList) {
			List<ImageVO> localImages = imageMapper.getImagesById(product.getPrdtId());
			imageMapper.deleteImagesPermanent(product.getPrdtId());
			for (ImageVO image : localImages) {
				ImageFileUtils.deleteImagesPermanent(image.getFileName());
			}
			// 상품 좋아요 삭제
			productMapper.deleteProductLikePermanent(product.getPrdtId());
		}
		// 상품 삭제
		productMapper.deleteProductPermanent(accountId);
		// 유저 삭제
		userMapper.deleteUserPermanent(userId, userEmail);
	}

	@Override
	public String getIdByNameAndPhone(UserVO user) {
		String findUserId = userMapper.getIdByNameAndPhone(user);
		if (findUserId == null) {
			throw new IllegalArgumentException("입력하신 정보를 가진 유저가 없음.");
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
	@Transactional
	public void changeUserPwd(UserVO user) {
		UserVO newUser = userMapper.getUserById(user.getUserId());
		String encodedPwd = PasswordEncryptor.encrypt(user.getUserPwd());
		UserVO changeUser = UserVO.builder()
				.accountId(newUser.getAccountId())
				.userPwd(encodedPwd)
				.build();
		userMapper.changeUserPwd(changeUser);
	}

	@Override
	@Transactional
	public void changeUserProfile(UserVO user) {
		String accountId = userMapper.getAccountId(user.getUserId());
		UserVO editUser = UserVO.builder()
				.accountId(accountId)
				.userEmail(user.getUserEmail())
				.userPhone(user.getUserPhone())
				.userAddr(user.getUserAddr())
				.build();
		userMapper.changeUserProfile(editUser);
	}

	@Override
	@Transactional(readOnly = true)
	public UserVO getUserById(String userId) {
		return userMapper.getUserById(userId);
	}

}
