package com.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.board.domain.UserVO;
import com.board.exception.EmailAlreadyExistsException;
import com.board.exception.InvalidValueException;
import com.board.exception.UserAlreadyExistsException;
import com.board.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

/*
  	@RestController @Controller에 @ResponseBody까지 합쳐진것입니다.
    주로 Http response로 view가 아닌 문자열과 JSON등을 보낼때 사용합니다.
    @RequestMapping 어노테이션은 URL을 컨트롤러의 클래스나 메서드와 매핑할 때 사용하는 스프링 프레임워크의 어노테이션입니다.
    @RequestBody는 HTTP 요청 body를 자바 객체로 변환합니다.
 */

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Log4j
public class UserController {

	private UserService service;
	
	/*
	 * 주요 기능 처리
	 */

	@PostMapping
	public ResponseEntity<Void> signUpUser(@RequestBody UserVO user) {
		service.signUpUser(user);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@GetMapping("/id/{userId}")
	public ResponseEntity<Void> checkUserId(@PathVariable("userId") String userId) throws Exception {

		try {
			service.isExistUserId(userId);
		} catch (UserAlreadyExistsException e) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/email/{userEmail:.*}")
	public ResponseEntity<Void> checkUserEmail(@PathVariable("userEmail") String userEmail) {

		try {
			service.isExistUserEmail(userEmail);
		} catch (EmailAlreadyExistsException e) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {

		try {
			service.deleteUser(userId);
			return new ResponseEntity<String>(userId + "님 탈퇴완료", HttpStatus.OK);
		} catch (InvalidValueException ive) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/id-inquiry", produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> findUserId(@RequestParam("userName") String userName) {
		String userId = service.findUserId(userName);
		String responseMsg = null;
		if (userId == null) {
			responseMsg = "존재하지 않는 사용자";
			return new ResponseEntity<String>(responseMsg,HttpStatus.CONFLICT);
		}
		responseMsg = userName + "님의 아이디는 " + userId + "입니다.";
		return new ResponseEntity<String>(responseMsg,HttpStatus.OK);
	}
	
	/*
	 * 페이지 이동 처리
	 */
	@GetMapping("/signup")
	public ModelAndView signUpForm() {
		return new ModelAndView("users/signup");
	}
	
	@PostMapping("/login")
	public ModelAndView loginFormPost() {
		return new ModelAndView("users/login");
	} 

	@GetMapping("/login")
	public ModelAndView loginFormGet() {
		return new ModelAndView("users/login");
	}

	@GetMapping("/idinquiry-input")
	public ModelAndView idInquiry() {
		return new ModelAndView("login/idInquiry");
	}

	@GetMapping("/pwdinquiry-input")
	public ModelAndView pwdInquiry() {
		return new ModelAndView("login/pwdInquiry");
	}

}
