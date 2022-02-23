package com.board.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * @Builder
 * : 해당 클래스의 빌더 패턴을 자동으로 생성해준다.
 * 
 * @Getter
 * : 각 필드에 대한 접근재 메소드 생성.
 * 
 * @AllArgsConstructor
 * : 모든 필드를 파라미터로 가지는 생성자를 만든다.
 * 
 * @NoArgsConstructor
 * : 파라미터가 없는 기본 생성자를 만든다. 
 * : MyBtis가 자동으로 객체를 생성할 때 기본적으로 파라미터가 없는 생성자가 필요하다.
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String userPwd;
	private String userName;
	private String userEmail;
	private String userPhone;
	private String userAddr;
	private Date userRegDate;
	private Date userDropDate;
	private String userAuth;
	private int userEnabled;

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
