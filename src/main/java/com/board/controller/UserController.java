package com.board.controller;

import org.apache.ibatis.annotations.Delete;
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

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
	
	private UserService service;

    @PostMapping("/signup")
    public String signUpUser(@RequestBody UserVO user, RedirectAttributes rttr) {
    	service.signUpUser(user);
    	rttr.addFlashAttribute("success", user.getUserId());
    	
    	return "/";
    }
    
    @GetMapping("/signup")
    public ModelAndView signUpForm() {
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("users/signUpForm");
    	return mv;
    }
    
    @GetMapping("/signup-id-check")
    public boolean checkUserId(@RequestParam("userId") String userId) {
    	boolean result = service.isExistUserId(userId);
    	return result;
    }
    
    @GetMapping("/email-check")
    public boolean checkUserEmail(@RequestParam("userEmail") String userEmail) {
    	boolean result = service.isExistUserEmail(userEmail);
    	return result;
    }
    
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") String userId, RedirectAttributes rttr) {
    	if (service.deleteUser(userId)) {
    		rttr.addFlashAttribute("result", "success");
    	}
    	return "/";
    }
	
}
