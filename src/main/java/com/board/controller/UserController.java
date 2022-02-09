package com.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.domain.UserVO;
import com.board.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
	
	private UserService service;

    @PostMapping("/signup")
    @ResponseBody
    public String signUpUser(@RequestBody UserVO user, RedirectAttributes rttr) {
    	service.signUpUser(user);
    	rttr.addFlashAttribute("success", user.getUserId());
    	
    	return "/";
    }
    
    @GetMapping("/signup")
    public String signUpForm() {
    	return "user/signUpForm";
    }
    
    @GetMapping("/signup-id-check")
    @ResponseBody
    public boolean checkUserId(@RequestParam("userId") String userId) {
    	boolean result = service.isExistUserId(userId);
    	return result;
    }
    
    @GetMapping("/email-check")
    @ResponseBody
    public boolean checkUserEmail(@RequestParam("userEmail") String userEmail) {
    	boolean result = service.isExistUserEmail(userEmail);
    	return result;
    }
    
    @PostMapping("/remove")
    public String deleteUser(@RequestParam("userId") String userId, RedirectAttributes rttr) {
    	if (service.deleteUser(userId)) {
    		rttr.addFlashAttribute("result", "success");
    	}
    	return "redirect:/";
    }
	
}
