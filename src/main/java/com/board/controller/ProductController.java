package com.board.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.board.domain.ProductVO;
import com.board.domain.UserVO;
import com.board.exception.InvalidValueException;
import com.board.exception.UserNotExistsException;
import com.board.service.ProductService;
import com.board.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Log4j
public class ProductController {
	
	private final UserService userService;
	
	private final ProductService productService;
	
	/*
	 * 비즈니스 로직
	 */
	
	@PostMapping("/new")
	public ResponseEntity<String> addNewProduct(@RequestPart List<MultipartFile> productImage,
			@RequestPart ProductVO product, @RequestPart UserVO user) {
		try {
			productService.addNewProduct(user.getUserId(), product, productImage);
		} catch (InvalidValueException e) {
			return new ResponseEntity<String>("에러 발생. 다시 요청해주세요.", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("상품 등록 완료.", HttpStatus.OK);
	}
	
	
	
	/*
	 * 페이지 호출
	 */
	
	
	@GetMapping("/new")
	public ModelAndView newProduct(Model model, Principal principal) {
		String username = principal.getName();
		if (username == null) {
			throw new UserNotExistsException("존재하지 않는 사용자입니다.");
		}
		model.addAttribute("users", userService.getUserById(username));
		return new ModelAndView("product/newProduct");
	}
	
	@GetMapping("/{prdtId}")
	public ModelAndView getProduct(@PathVariable("prdtId") String prdtId, Model model) {
		ProductVO selectedProduct = productService.getProductById(prdtId);
		model.addAttribute("product", selectedProduct);
		return new ModelAndView("product/productDetail");
	}
	
	@GetMapping("/shop")
	public ModelAndView myShop() {
		return new ModelAndView("product/shop");
	}
}
