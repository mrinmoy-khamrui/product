package com.myretail.product.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.myretail.product.domain.Money;
import com.myretail.product.domain.mongo.ProductPrice;
import com.myretail.product.repository.ProductPriceRepository;
import com.myretail.product.service.dto.PriceDTO;
import com.myretail.product.service.dto.ProductDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerIT {
	
	private static final int PRODUCT_ID = 13860428;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired 
	private ProductPriceRepository priceRepository;
	
	@BeforeEach
	public void setupPrice() {
		ProductPrice price = new ProductPrice();
		price.setProductId(PRODUCT_ID);
		price.setCurrency(Currency.getInstance("USD"));
		price.setValue(BigDecimal.valueOf(12.33));
		priceRepository.save(price);
	}
	
	@Test
	public void testFindById() {
		ProductDTO dto = restTemplate.getForObject("http://localhost:{port}/api/v1/products/{id}", ProductDTO.class, port, PRODUCT_ID);
		assertThat(dto.getId()).isEqualTo(PRODUCT_ID);
		assertThat(dto.getName()).isNotBlank();
		assertThat(dto.getCurrentPrice()).isNotNull();
	}
	
	@Test
	public void testUpdate() {
		ProductDTO dto = new ProductDTO(PRODUCT_ID, "Test", new PriceDTO(new Money(BigDecimal.valueOf(99.99))));
		restTemplate.put("http://localhost:{port}/api/v1/products/{id}", dto, port, PRODUCT_ID);
		assertThat(priceRepository.findById(PRODUCT_ID).get().getValue()).isEqualByComparingTo(dto.getCurrentPrice().getValue());
	}
}
