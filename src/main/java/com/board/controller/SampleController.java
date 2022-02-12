package com.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample/*")
public class SampleController {
	
	@GetMapping("/all")
	public void all() {
		
	}
	
	@GetMapping("/member")
	public void member() {
		
	}
	
	@GetMapping("/admin")
	public void admin() {
		
	}

}
