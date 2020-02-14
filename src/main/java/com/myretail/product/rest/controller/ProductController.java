package com.myretail.product.rest.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myretail.product.service.ProductService;
import com.myretail.product.service.dto.ProductDTO;

/**
 * ReST Controller for products resource
 * 
 * @author mrinmoy
 *
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
	
	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = Objects.requireNonNull(productService);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ProductDTO findById(@PathVariable int id) {
		return productService.getProduct(id);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public void update(@PathVariable int id, @Valid @RequestBody ProductDTO productDTO) {
		if(id != productDTO.getId()) {
			throw new IllegalArgumentException("id does not match");
		}
		productService.updateProduct(productDTO);
	}
}
