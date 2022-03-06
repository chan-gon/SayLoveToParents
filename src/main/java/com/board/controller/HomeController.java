package com.board.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.board.domain.ProductVO;
import com.board.service.ProductService;

import lombok.RequiredArgsConstructor;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private final ProductService productService;

	/**
	 *	로그인 이후의 첫 페이지
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		List<ProductVO> productList = productService.getProductList();

		model.addAttribute("imagePath", "/productImages/");
		model.addAttribute("products", productList);

		return "users/main";
	}

	/**
	 *	사이트 첫 페이지
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login() {
		return "users/login";
	}

}
