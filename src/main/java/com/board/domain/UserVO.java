package com.board.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class UserVO implements UserDetails {

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
