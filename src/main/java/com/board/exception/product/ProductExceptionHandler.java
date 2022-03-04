package com.board.exception.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.board.controller.ProductController;
import com.board.exception.common.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackageClasses = { ProductController.class })
public class ProductExceptionHandler {

	@ExceptionHandler(ProductException.class)
	public ApiResponse productExceptionHandler(ProductException e) {
		log.warn("===== START =====");
		log.warn("{}...", e.getClass(), e);
		log.warn("===== END =====");
		return new ApiResponse(HttpStatus.OK, e.getProductExceptionMessage().getMessage());
	}
}
