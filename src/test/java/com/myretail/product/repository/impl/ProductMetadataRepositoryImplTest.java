package com.myretail.product.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import com.myretail.product.exception.ProductNotFoundException;

public class ProductMetadataRepositoryImplTest {
	
	private ProductMetadataRepositoryImpl productMetadataRepository;
	
	@BeforeEach
	public void setup() {
		RestTemplate redskyRestTemplate = new RestTemplate();
		String redskyUriTemplate = "https://redsky.target.com/v2/pdp/tcin/{id}?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
		productMetadataRepository = new ProductMetadataRepositoryImpl(redskyRestTemplate, redskyUriTemplate);
	}

	@Test
	public void testGetNameSuccess() {
		String name = productMetadataRepository.getProductName(13860428);
		assertThat(name).isEqualTo("The Big Lebowski (Blu-ray)");
	}
	
	@Test
	public void testGetNameForMissingProduct() {
		assertThrows(ProductNotFoundException.class, () -> productMetadataRepository.getProductName(123));
	}
}
