package com.board.controller;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.domain.UserVO;
import com.board.exception.EmailAlreadyExistsException;
import com.board.exception.InvalidValueException;
import com.board.exception.UserAlreadyExistsException;
import com.board.service.UserService;
import com.board.utils.EmailUtils;

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
	public ResponseEntity<String> signUpUser(@RequestBody UserVO user) {
		try {
			service.signUpUser(user);
		} catch (UserAlreadyExistsException e) {
			return new ResponseEntity<String>("에러 발생. 다시 요청해주세요." ,HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("회원가입 완료" ,HttpStatus.CREATED);
	}

	@GetMapping("/signup/id")
	public ResponseEntity<String> checkUserId(@RequestParam("userId") String userId) {
		log.warn("userId ======== " + userId);
		try {
			service.isExistUserId(userId);
		} catch (UserAlreadyExistsException e) {
			return new ResponseEntity<String>("이미 존재하는 아이디.", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("사용 가능한 아이디.", HttpStatus.OK);
	}

	@GetMapping("/signup/email")	
	public ResponseEntity<String> checkUserEmail(@RequestParam("userEmail") String userEmail) {
		log.warn("userEmail ======== " + userEmail);
		try {
			service.isExistUserEmail(userEmail);
		} catch (EmailAlreadyExistsException e) {
			return new ResponseEntity<String>("이미 존재하는 이메일.", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("사용 가능한 이메일.", HttpStatus.OK);
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
	
	@PostMapping(value = "/help/id", produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> findUserId(@RequestBody UserVO user) {
		String userId = service.findUserId(user.getUserName(), user.getUserPhone());
		String responseMsg = null;
		if (userId == null) {
			responseMsg = "존재하지 않는 사용자";
			return new ResponseEntity<String>(responseMsg,HttpStatus.CONFLICT);
		}
		responseMsg = user.getUserName() + "님의 아이디는 " + userId + "입니다.";
		return new ResponseEntity<String>(responseMsg,HttpStatus.OK);
	}
	
	@GetMapping(value = "/help/pwd/email", produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> sendCertEmail(@RequestParam("userId") String userId, @RequestParam("userEmail") String userEmail) {
		
		int cnt = service.checkUserIdEmail(userId, userEmail);
		if (cnt == 0) {
			return new ResponseEntity<String>("잘못된 아이디 또는 이메일 주소" ,HttpStatus.CONFLICT);
		}
		Random random = new Random();
        int certNum = random.nextInt(888888) + 111111;
	    String code = "";
	    
	    try {
	    	EmailUtils.sendEmail(userEmail, certNum);
	    	code = Integer.toString(certNum);
	    	log.warn("code ======== " + code);
	    } catch (Exception e) {
	    	return new ResponseEntity<String>("에러 발생. 다시 요청해주세요." ,HttpStatus.BAD_REQUEST);
	    }
		return new ResponseEntity<String>(code ,HttpStatus.OK);
	}
	
	@PostMapping(value = "/help/pwd/{userId}", produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> pwdChange(@RequestBody UserVO user, @PathVariable("userId") String userId) {
		try {
			user.setUserId(userId);
			service.changeUserPwd(user);
		} catch (Exception e) {
			return new ResponseEntity<String>("에러 발생. 다시 요청해주세요." ,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("비밀번호 변경 완료" ,HttpStatus.OK);
	}
	
	@PostMapping(value = "/profile", produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> profileEdit(@RequestBody UserVO user) {
		try {
			service.changeUserProfile(user);
		} catch (Exception e) {
			return new ResponseEntity<String>("에러 발생. 다시 요청해주세요.", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("수정 완료." ,HttpStatus.OK);
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
		return new ModelAndView("login/inquiry/idInquiry");
	}

	@GetMapping("/help/pwd")
	public ModelAndView pwdInquiryForm() {
		return new ModelAndView("login/inquiry/pwdInquiry");
	}
	
	@GetMapping("/help/pwd/{userId}")
	public ModelAndView pwdChangeForm(@PathVariable("userId") String userId, RedirectAttributes rttr) {
		ModelAndView mv = new ModelAndView();
		rttr.addAttribute("userId", userId);
		mv.setViewName("/login/inquiry/pwdChange");
		return mv;
	}
	
	@GetMapping("/profile")
	public ModelAndView profileForm(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		log.warn("userId ============== " + username);
		model.addAttribute("user", service.selectByUserId(username));
		return new ModelAndView("users/profile");
	}

}
