package com.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	/*
	 * 비즈니스 로직
	 */
	
	
	/*
	 * 페이지 호출
	 */
	
	@GetMapping("/new")
	public ModelAndView newProduct() {
		return new ModelAndView("product/newProduct");
	}

}
