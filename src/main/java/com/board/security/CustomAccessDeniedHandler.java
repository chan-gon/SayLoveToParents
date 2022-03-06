package com.board.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/*
 * Spring Security를 통한 로그인 작업 시 403 에러를 처리하기 위한 핸들러 클래스
 */

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		System.err.println("!!!!!!!!!!!!!!!!!!!!!!!");
		if (accessDeniedException instanceof AccessDeniedException) {
			request.setAttribute("ERROR_MSG", "접근 권한이 없는 사용자");
		}
		
		request.getRequestDispatcher("error/403").forward(request, response);
	}

}
