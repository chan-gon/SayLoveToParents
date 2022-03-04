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

import com.board.domain.ImageVO;
import com.board.domain.ProductVO;
import com.board.domain.UserVO;
import com.board.service.ImageService;
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
	
	private final ImageService imageService;
	
	/*
	 * 비즈니스 로직
	 */
	
	@PostMapping("/new")
	public void addNewProduct(@RequestPart List<MultipartFile> productImage,
			@RequestPart ProductVO product, @RequestPart UserVO user) {
		productService.addNewProduct(user.getUserId(), product, productImage);
	}
	
	@PostMapping(value = "/like/{prdtId}", produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> likeProduct(@PathVariable("prdtId") String prdtId) {
		try {
			productService.likeProuct(prdtId);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>("에러 발생. 다시 요청해주세요.", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("찜하기 완료.", HttpStatus.OK);
	}
	
	@PostMapping(value = "/unlike/{prdtId}", produces = "application/text; charset=UTF-8")
	public ResponseEntity<String> unlikeProduct(@PathVariable("prdtId") String prdtId) {
		try {
			productService.unlikeProuct(prdtId);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>("에러 발생. 다시 요청해주세요.", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("찜하기 취소 완료.", HttpStatus.OK);
	}
	
	@PostMapping("/delete/{prdtId}")
	public void deleteProduct(@PathVariable("prdtId") String prdtId, Principal principal) {
		productService.deleteProduct(principal, prdtId);
	}
	
	/*
	 * 페이지 호출
	 */
	
	@GetMapping("/new")
	public ModelAndView newProduct(Model model, Principal principal) {
		try {
			String username = principal.getName();
			model.addAttribute("users", userService.getUserById(username));
			return new ModelAndView("product/newProduct");
		} catch (NullPointerException e) {
			throw new NullPointerException();
		}
	}
	
	@GetMapping("/{prdtId}")
	public ModelAndView getProduct(@PathVariable("prdtId") String prdtId, Model model) {
		ProductVO selectedProduct = productService.getProductById(prdtId);
		List<ImageVO> productImages = imageService.getImagesById(prdtId);
		model.addAttribute("images", productImages);
		model.addAttribute("product", selectedProduct);
		return new ModelAndView("product/productDetail");
	}
	
	@GetMapping("/shop")
	public ModelAndView myShop(Principal principal, Model model) {
		List<ProductVO> productList = productService.getProductListById(principal);
		model.addAttribute("products", productList);
		return new ModelAndView("product/shop");
	}
}
