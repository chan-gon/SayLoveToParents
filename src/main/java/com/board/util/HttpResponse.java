package com.board.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpResponse {

	// 200 OK 요청이 성공했음
	public static final ResponseEntity RESPONES_OK = new ResponseEntity(HttpStatus.OK);

	// 201 Created 요청이 성공적으로 처리되었고 자원이 생성되음
	public static final ResponseEntity RESPONSE_CREATED = new ResponseEntity(HttpStatus.CREATED);

	// 400 Bad Request 잘못된 클라이언트 요청으로 인한 서버의 거절
	public static final ResponseEntity RESPONSE_BAD_REQUEST = new ResponseEntity(HttpStatus.BAD_REQUEST);

	// 401 Unauthorized 해당 리소스에 유효한 인증 자격 증명이 없음
	public static final ResponseEntity RESPONSE_UNAUTHORIZED = new ResponseEntity(HttpStatus.UNAUTHORIZED);

	// 404 요청한 페이지 찾을 수 없음
	public static final ResponseEntity RESPONSE_NOT_FOUND = new ResponseEntity(HttpStatus.NOT_FOUND);

	// 409 서버의 현재 상태와 요청이 충돌
	public static final ResponseEntity RESPONSE_CONFLICT = new ResponseEntity(HttpStatus.CONFLICT);

}
