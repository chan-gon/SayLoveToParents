package com.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.domain.UserVO;
import com.board.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/user/*")
@AllArgsConstructor
public class UserController {
	
	private UserService service;

    @PostMapping("/signup")
    public String signUpUser(UserVO user, RedirectAttributes rttr) {
    	service.signUpUser(user);
    	rttr.addFlashAttribute("success", user.getUserId());
    	
    	return "redirect:/";
    }
    
    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam("userId") String userId, RedirectAttributes rttr) {
    	if (service.deleteUser(userId)) {
    		rttr.addFlashAttribute("result", "success");
    	}
    	return "redirect:/";
    }
	
}
