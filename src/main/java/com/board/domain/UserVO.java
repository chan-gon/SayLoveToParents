package com.board.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserVO {
	
	private String userId;
	private String userPwd;
	private String userName;
	private String userEmail;
	private String userPhone;
	private String userAddr;
	private Date userRegDate;
	private Date userDropDate;

}
