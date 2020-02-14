package com.myretail.product.rest.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.myretail.product.exception.PriceNotFoundException;
import com.myretail.product.exception.ProductNotFoundException;

@ControllerAdvice
public class RestExceptionTranslator {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionTranslator.class);
	
	@ExceptionHandler
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException e) {
		return new ResponseEntity<ErrorDTO>(ErrorDTO.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
    public ResponseEntity<ErrorDTO> handlePriceNotFoundException(PriceNotFoundException e) {
		return new ResponseEntity<ErrorDTO>(ErrorDTO.PRICE_NOT_FOUND, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException e) {
		LOGGER.warn("Validation Error: {}", e.getMessage());
		return new ResponseEntity<ErrorDTO>(ErrorDTO.INPUT_VALIDATION_FAILED, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
		LOGGER.warn("validation error {}", e.getMessage());
		return new ResponseEntity<ErrorDTO>(ErrorDTO.INPUT_VALIDATION_FAILED, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
    public ResponseEntity<ErrorDTO> handleOtherException(Exception e) {
		LOGGER.error("Unhandled Error", e);
		return new ResponseEntity<ErrorDTO>(ErrorDTO.UNKNOWN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
