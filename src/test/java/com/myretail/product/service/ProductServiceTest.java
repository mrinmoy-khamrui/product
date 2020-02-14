package com.myretail.product.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;

import com.myretail.product.domain.mongo.ProductPrice;
import com.myretail.product.exception.PriceNotFoundException;
import com.myretail.product.repository.ProductMetadataRepository;
import com.myretail.product.repository.ProductPriceRepository;
import com.myretail.product.service.dto.ProductDTO;
import com.myretail.product.service.mapper.ProductDTOMapper;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductServiceTest {
	
	private ProductPrice pp;

	@MockBean
	private ProductMetadataRepository metadataRepository;
	
	@Autowired
	private ProductPriceRepository productPriceRepository;
	
	private ProductDTOMapper productDTOMapper = new ProductDTOMapper();
	
	@BeforeEach
	public void setupPrice() {
		pp = new ProductPrice();
		pp.setCurrency(Currency.getInstance("USD"));
		pp.setProductId(100);
		pp.setValue(BigDecimal.valueOf(99.99));
		productPriceRepository.save(pp);
	}
	
	@Test
	public void testPriceNotFoundException() {
		when(metadataRepository.getProductName(anyInt())).thenReturn("Test Product");
		ProductService service = new ProductService(metadataRepository, productPriceRepository, productDTOMapper);
		assertThrows(PriceNotFoundException.class, () -> service.getProduct(123));
	}
	
	@Test
	public void testGetProductSuccess() {
		when(metadataRepository.getProductName(anyInt())).thenReturn("Test Product");
		ProductService service = new ProductService(metadataRepository, productPriceRepository, productDTOMapper);
		ProductDTO dto = service.getProduct(pp.getProductId());
		assertEquals("Test Product", dto.getName());
		assertNotNull(dto.getCurrentPrice());
	}
}
