package com.board.controller;

import java.security.Principal;

import org.apache.commons.mail.EmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.domain.UserVO;
import com.board.exception.EmailAlreadyExistsException;
import com.board.exception.InvalidValueException;
import com.board.exception.UserAlreadyExistsException;
import com.board.exception.UserNotExistsException;
import com.board.service.UserService;
import com.board.util.EmailUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

/*
  	@RestController @Controller에 @ResponseBody까지 합쳐진것입니다.
    주로 Http response로 view가 아닌 문자열과 JSON등을 보낼때 사용합니다.
    @RequestMapping 어노테이션은 URL을 컨트롤러의 클래스나 메서드와 매핑할 때 사용하는 스프링 프레임워크의 어노테이션입니다.
    @RequestBody는 HTTP 요청 body를 자바 객체로 변환합니다.
 */

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j
public class UserController {

	private final UserService userService;

	/*
	 * 사용자 요청 처리
	 */

	@PostMapping
	public ResponseEntity<String> signUpUser(@RequestBody UserVO user) {
		try {
			userService.signUpUser(user);
		} catch (UserAlreadyExistsException e) {
			return new ResponseEntity<String>("에러 발생. 다시 요청해주세요.", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("회원가입 완료", HttpStatus.CREATED);
	}

	@GetMapping("/signup/id")
	public ResponseEntity<String> checkUserId(@RequestParam("userId") String userId) {
		try {
			userService.isExistUserId(userId); 
		} catch (UserAlreadyExistsException e) {
			return new ResponseEntity<String>("이미 존재하는 아이디.", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("사용 가능한 아이디.", HttpStatus.OK);
	}

	@GetMapping("/signup/email")
	public ResponseEntity<String> checkUserEmail(@RequestParam("userEmail") String userEmail) {
		try {
			userService.isExistUserEmail(userEmail);
		} catch (EmailAlreadyExistsException e) {
			return new ResponseEntity<String>("이미 존재하는 이메일.", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("사용 가능한 이메일.", HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId, @RequestParam("userPwd") String inputPwd, @RequestBody UserVO user) {
		try {
			userService.deleteUser(inputPwd, user);
		} catch (InvalidValueException ive) {
			return new ResponseEntity<String>("에러 발생. 다시 요청해주세요.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(userId + "님 탈퇴완료", HttpStatus.OK);
	}

	@PostMapping(value = "/help/id", produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> findUserId(@RequestBody UserVO user) {
		String userId = null;
		try {
			userId = userService.getIdByNameAndPhone(user);
		} catch (InvalidValueException e) {
			return new ResponseEntity<String>("이름 또는 전화번호가 잘못 입력되었습니다.", HttpStatus.BAD_REQUEST);
		}
		if (userId == null) {
			return new ResponseEntity<String>("사용자 정보가 존재하지 않습니다.", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("아이디를 찾았습니다: " + userId, HttpStatus.OK);
	}

	@GetMapping(value = "/help/pwd/email", produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> sendCertEmail(@RequestParam("userId") String userId, @RequestParam("userEmail") String userEmail) throws EmailException {

		String certNum = EmailUtils.getCertNum();

		try {
			userService.isValidIdAndEmail(userId, userEmail);

			EmailUtils.sendEmail(userEmail, certNum);
			log.warn("이메일 인증번호 ======== " + certNum);
		} catch (UserNotExistsException e) {
			return new ResponseEntity<String>("사용자 정보가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(certNum, HttpStatus.OK);
	}

	@PostMapping(value = "/help/pwd", produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> pwdChange(@RequestBody UserVO user) {
		try {
			userService.changeUserPwd(user);
		} catch (Exception e) {
			return new ResponseEntity<String>("에러 발생. 다시 요청해주세요.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("비밀번호 변경 완료", HttpStatus.OK);
	}

	@PostMapping(value = "/profile", produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> profileEdit(@RequestBody UserVO user) {
		try {
			userService.changeUserProfile(user);
		} catch (Exception e) {
			return new ResponseEntity<String>("에러 발생. 다시 요청해주세요.", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("수정 완료.", HttpStatus.OK);
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

	@GetMapping("/help/id")
	public ModelAndView idInquiryForm() {
		return new ModelAndView("login/idInquiry");
	}

	@GetMapping("/help/pwd")
	public ModelAndView pwdInquiryForm() {
		return new ModelAndView("login/pwdInquiry");
	}

	@GetMapping("/help/pwd/{userId}")
	public ModelAndView pwdChangeForm(@PathVariable("userId") String userId, RedirectAttributes rttr) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/login/pwdChange");
		return mv;
	}

	@GetMapping("/profile")
	public ModelAndView profileForm(Model model, Principal principal) {
		String username = principal.getName();
		if (username == null) {
			throw new AccessDeniedException("접근 권한이 없는 사용자");
		}
		model.addAttribute("users", userService.getUserById(username));
		return new ModelAndView("users/profile");
	}

}
