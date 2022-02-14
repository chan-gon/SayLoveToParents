package com.board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.board.domain.UserVO;
import com.board.mapper.UserMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {

	@Setter(onMethod_ = @Autowired)
	private UserMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserVO user = mapper.getUserInfoById(username);

		if (user == null) {
			throw new InternalAuthenticationServiceException(username);
		}
		
		if (!user.isEnabled() || !user.isCredentialsNonExpired()) {
			throw new AuthenticationCredentialsNotFoundException(username);
		}
		
		log.warn("Load User By UserName = " + username);

		return user;
	}

}
