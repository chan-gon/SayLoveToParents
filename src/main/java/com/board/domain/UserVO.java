package com.board.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/*
 * @Builder
 * : 해당 클래스의 빌더 패턴을 자동으로 생성해준다.
 * 
 * @Getter
 * : 각 필드에 대한 접근재 메소드 생성.
 * 
 * @NoArgsConstructor(force = true)
 * : 파라미터가 없는 기본 생성자를 만든다.
 *   (force = true) 설정을 통해 final 필드를 기본값(0, false, null)으로 초기화 한다.
 * 
 * @RequiredArgsConstructor
 * : @NonNull 어노테이션 또는 final 키워드가 있는 필드를 파라미터로 가지는 생성자를 만든다.
 */

@Getter
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class UserVO implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final String userId;
	private final String userPwd;
	private final String userName;
	private final String userEmail;
	private final String userPhone;
	private final String userAddr;
	private final Date userRegDate;
	private final Date userDropDate;
	private final String userAuth;
	private final int userEnabled;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		auth.add(new SimpleGrantedAuthority(userAuth));
		return auth;
	}

	@Override
	public String getPassword() {
		return userPwd;
	}

	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getUserName() {
		return userName;
	}

}
