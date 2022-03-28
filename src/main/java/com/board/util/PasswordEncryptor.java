package com.board.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncryptor {

	public static String encrypt(String rawPassword) {
		return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
	}

	public static boolean isMatch(String rawPassword, String encodedPassword) {
		return BCrypt.checkpw(rawPassword, encodedPassword);
	}
}
