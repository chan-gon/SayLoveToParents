package com.board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.board.domain.UserVO;
import com.board.utils.PasswordEncryptor;

import lombok.Setter;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Setter(onMethod_ = @Autowired)
	private UserDetailsService service;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		UserVO user = (UserVO) service.loadUserByUsername(username);

		if (!PasswordEncryptor.isMatch(password, user.getPassword())) {
			throw new BadCredentialsException(username);
		}

		if (!user.isEnabled()) {
			throw new BadCredentialsException(username);
		}
		return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
	}

	/*
	 * authenticate 메소드의 리턴 객체 타입이 유효한 타입인지 메소드.
	 * AuthenticationProvider 인터페이스의 실제 구현체가 Spring Security에서 규정한 타입이 맞는지 확인하고, true를 반환해야 
	 * 최종적으로 인증에 성공한다. 
	 * null 값이거나 잘못된 타입을 반환했을 경우 인증 실패로 간주한다.
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		// Spring Security가 요구하는 UsernamePasswordAuthenticationToken 타입이 맞는지 확인
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
