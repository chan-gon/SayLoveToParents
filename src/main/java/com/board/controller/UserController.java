package com.board.controller;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;

import org.apache.commons.mail.EmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public void signUpUser(@RequestBody UserVO user) {
		userService.signUpUser(user);
	}

	@GetMapping("/signup/id")
	public void checkUserId(@RequestParam("userId") String userId) {
		userService.isExistUserId(userId);
	}

	@GetMapping("/signup/email")
	public void checkUserEmail(@RequestParam("userEmail") String userEmail) {
		userService.isExistUserEmail(userEmail);
	}

	@DeleteMapping
	public void deleteUser(@RequestBody UserVO user) {
		userService.deleteUserPermanent(user.getUserId(), user.getUserEmail());
	}

	@PostMapping(value = "/help/id", produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> findUserId(@RequestBody UserVO user) {
		String userId = userService.getIdByNameAndPhone(user);
		return new ResponseEntity<String>("아이디를 찾았습니다: " + userId, HttpStatus.OK);
	}

	@GetMapping(value = "/help/pwd/email", produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> sendCertEmail(@RequestParam("userId") String userId, @RequestParam("userEmail") String userEmail) throws EmailException, NoSuchAlgorithmException {
		String certNum = EmailUtils.getCertNum();
		try {
			userService.isValidIdAndEmail(userId, userEmail);
			EmailUtils.sendEmail(userEmail, certNum);
			log.warn("이메일 인증번호 ======== " + certNum);
		} catch (RuntimeException e) {
			return new ResponseEntity<String>("사용자 정보가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(certNum, HttpStatus.OK);
	}

	@PostMapping(value = "/help/pwd", produces = "application/text; charset=UTF-8")
	public void pwdChange(@RequestBody UserVO user) {
		userService.changeUserPwd(user);
	}

	@PostMapping(value = "/profile", produces = "application/text; charset=UTF-8")
	public void profileEdit(@RequestBody UserVO user) {
		userService.changeUserProfile(user);
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
		try {
			String username = principal.getName();
			model.addAttribute("users", userService.getUserById(username));
			return new ModelAndView("users/profile");
		} catch (NullPointerException e) {
			throw new NullPointerException();
		}
	}
	
	@GetMapping("/accessdenied")
	public ModelAndView accessDeniedErrorPage() {
		return new ModelAndView("error/403");
	}
	
	@GetMapping("/delete")
	public ModelAndView deletePage() {
		return new ModelAndView("users/delete");
	}
}
