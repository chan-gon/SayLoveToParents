package com.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.board.domain.UserVO;
import com.board.exception.EmailAlreadyExistsException;
import com.board.exception.InvalidValueException;
import com.board.exception.UserAlreadyExistsException;
import com.board.service.UserService;

import lombok.AllArgsConstructor;

/*
 * @RestController
   - @Controller에 @ResponseBody가 추가된 어노테이션
   - @RequestMapping 메소드를 처리할 때 @ResponseBody가 기본적으로 붙으면서 처리
 */

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

	private UserService service;

	@PostMapping("")
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
			return new ResponseEntity<String>(userId + "님 탈퇴완료",HttpStatus.OK);
		} catch (InvalidValueException ive) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/signup")
	public ModelAndView signUpForm() {
		return new ModelAndView("users/signup");
	}
	
	@GetMapping("/login")
	public ModelAndView loginForm() {
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
	
	@GetMapping("/idinquiry")
	public String findUserId(@RequestBody UserVO user) {
		service.findUserId(user);
		return "";
	}

}
