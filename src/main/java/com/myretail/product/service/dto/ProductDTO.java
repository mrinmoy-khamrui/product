package com.myretail.product.service.dto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ProductDTO {

	@Min(1)
	@Max(Integer.MAX_VALUE)
	private int id;
	
	@NotEmpty
	private String name;
	
	@Valid
	private PriceDTO currentPrice;
	
	public ProductDTO() {
		
	}
	
	public ProductDTO(int id, String name, PriceDTO price) {
		this.id = id;
		this.name = name;
		this.currentPrice = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PriceDTO getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(PriceDTO currentPrice) {
		this.currentPrice = currentPrice;
	}
}
