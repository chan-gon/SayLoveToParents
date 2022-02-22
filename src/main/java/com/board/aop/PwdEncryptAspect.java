package com.board.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.board.domain.UserVO;
import com.board.utils.PasswordEncryptor;

import lombok.extern.log4j.Log4j;

/*
 * signUpUser(회원가입), changeUserPwd(비밀번호 변경) 메소드 호출 시
 * 비밀번호 암호화 기능을 공통으로 호출하기 때문에 Spring AOP 기능을 통해 
 * '관심사'를 분리하여 UserServiceImpl 클래스에는 비즈니스 로직만 작성하도록 구현했습니다.
 */

@Aspect
@Component
@Log4j
public class PwdEncryptAspect {
	
	@Pointcut("execution(* com.board.service..*Impl.signUpUser(..))")
	public void signUpUser() {}
	
	@Pointcut("execution(* com.board.service..*Impl.changeUserPwd(..))")
	public void changeUserPwd() {}
	
	@Before("changeUserPwd() || signUpUser()")
	public void changeUserPwdAspect(JoinPoint jp) {
		Object[] args = jp.getArgs();
		
		for (Object arg : args) {
			if (arg instanceof UserVO) {
				// 클래스 캐스팅
				UserVO user = UserVO.class.cast(arg);
				String encodedPwd = PasswordEncryptor.encrypt(user.getUserPwd());
				log.info("encodedPwd = " + encodedPwd);
				user.setUserPwd(encodedPwd);
			}
		}
	}

}
