package com.myretail.product.rest.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorDTO {

	PRODUCT_NOT_FOUND("product.notFound", "Product metadata for given input is not found"),
	PRICE_NOT_FOUND("price.notFound", "Price for given product id not found"),
	INPUT_VALIDATION_FAILED("invalid.input", "Input validation failed"),
	UNKNOWN_ERROR("unknown.error", "Internal problem");
	
	
	private String code;
	private String message;
	
	private ErrorDTO(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
