package com.board.exception.product;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.board.controller.ProductController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackageClasses = { ProductController.class })
public class ProductExceptionHandler {

	@ExceptionHandler(ProductException.class)
	public ModelAndView productExceptionHandler(ProductException e, Model model) {
		log.warn("===== START =====");
		log.warn("{}...", e.getClass(), e);
		log.warn("===== END =====");
		model.addAttribute("EXCEPTION_MSG", e.getProductExceptionMessage().getMessage());
		return new ModelAndView("error/exception");
	}
}
