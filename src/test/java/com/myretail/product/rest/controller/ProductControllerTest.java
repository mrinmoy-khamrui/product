package com.myretail.product.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyInt;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClientException;
import com.myretail.product.domain.Money;
import com.myretail.product.exception.PriceNotFoundException;
import com.myretail.product.exception.ProductNotFoundException;
import com.myretail.product.rest.error.ErrorDTO;
import com.myretail.product.service.ProductService;
import com.myretail.product.service.dto.PriceDTO;
import com.myretail.product.service.dto.ProductDTO;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	private ProductDTO dto = new ProductDTO(10, "Test", new PriceDTO(new Money(BigDecimal.valueOf(10.55))));
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;
	
	@Test
	public void testFindByIdSuccess() throws Exception {
		when(productService.getProduct(anyInt())).thenReturn(dto);
		this.mockMvc.perform(get("/api/v1/products/{id}", dto.getId()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(dto.getId()))
	        .andExpect(jsonPath("$.name").value(dto.getName()))
	        .andExpect(jsonPath("$.current_price.value").value(dto.getCurrentPrice().getValue()))
	        .andExpect(jsonPath("$.current_price.currency_code").value(dto.getCurrentPrice().getCurrencyCode()));
	}
	
	@Test
	public void testFindByIdProductNotFound() throws Exception {
		when(productService.getProduct(anyInt())).thenThrow(new ProductNotFoundException());
		this.mockMvc.perform(get("/api/v1/products/{id}", dto.getId()))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.code").value(ErrorDTO.PRODUCT_NOT_FOUND.getCode()))
	        .andExpect(jsonPath("$.message").value(ErrorDTO.PRODUCT_NOT_FOUND.getMessage()));
	}
	
	@Test
	public void testFindByIdPriceNotFound() throws Exception {
		when(productService.getProduct(anyInt())).thenThrow(new PriceNotFoundException());
		this.mockMvc.perform(get("/api/v1/products/{id}", dto.getId()))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value(ErrorDTO.PRICE_NOT_FOUND.getCode()))
	        .andExpect(jsonPath("$.message").value(ErrorDTO.PRICE_NOT_FOUND.getMessage()));
	}
	
	@Test
	public void testFindByIdMongoDown() throws Exception {
		when(productService.getProduct(anyInt())).thenThrow(new MongoClientException("mongod down"));
		this.mockMvc.perform(get("/api/v1/products/{id}", dto.getId()))
			.andDo(print())
			.andExpect(status().isInternalServerError())
			.andExpect(jsonPath("$.code").value(ErrorDTO.UNKNOWN_ERROR.getCode()))
	        .andExpect(jsonPath("$.message").value(ErrorDTO.UNKNOWN_ERROR.getMessage()));
	}
	
	@Test
	public void testFindByIdRedskyDown() throws Exception {
		when(productService.getProduct(anyInt())).thenThrow(new RestClientException("unknown host"));
		this.mockMvc.perform(get("/api/v1/products/{id}", dto.getId()))
			.andDo(print())
			.andExpect(status().isInternalServerError())
			.andExpect(jsonPath("$.code").value(ErrorDTO.UNKNOWN_ERROR.getCode()))
	        .andExpect(jsonPath("$.message").value(ErrorDTO.UNKNOWN_ERROR.getMessage()));
	}
	
	@Test
	public void testUpdateProductSuccess() throws Exception {
		//doAnswer((i) -> null).when(productService).updateProduct(any(ProductDTO.class));
		doNothing().when(productService).updateProduct(Matchers.any(ProductDTO.class));
		this.mockMvc.perform(put("/api/v1/products/{id}", dto.getId())
				.content(objectMapper.writeValueAsString(dto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateProductIdInputMismatch() throws Exception {
		doNothing().when(productService).updateProduct(Matchers.any(ProductDTO.class));
		this.mockMvc.perform(put("/api/v1/products/{id}", 111)
				.content(objectMapper.writeValueAsString(dto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value(ErrorDTO.INPUT_VALIDATION_FAILED.getCode()))
		        .andExpect(jsonPath("$.message").value(ErrorDTO.INPUT_VALIDATION_FAILED.getMessage()));
	}
	
	@Test
	public void testUpdateProductEmptyJson() throws Exception {
		//doAnswer((i) -> null).when(productService).updateProduct(any(ProductDTO.class));
		doNothing().when(productService).updateProduct(Matchers.any(ProductDTO.class));
		this.mockMvc.perform(put("/api/v1/products/{id}", 111)
				.content("{}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value(ErrorDTO.INPUT_VALIDATION_FAILED.getCode()))
		        .andExpect(jsonPath("$.message").value(ErrorDTO.INPUT_VALIDATION_FAILED.getMessage()));
	}
}
