package com.myretail.product.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myretail.product.domain.Money;
import com.myretail.product.domain.Product;
import com.myretail.product.service.dto.PriceDTO;
import com.myretail.product.service.dto.ProductDTO;

public class ProductDTOMapperTest {

	private ProductDTOMapper mapper;
	
	@BeforeEach
    public void setUp() {
		mapper = new ProductDTOMapper();
	}
	
	@Test
    public void testEntityToDTO() {
		Product p = new Product(10, "Test product", new Money(BigDecimal.ONE));
		ProductDTO d = mapper.toDto(p);
		assertThat(d.getId()).isEqualTo(p.getId());
		assertThat(d.getName()).isEqualTo(p.getName());
		assertThat(d.getCurrentPrice().getCurrencyCode()).isEqualTo(p.getPrice().getCurrency().getCurrencyCode());
		assertThat(d.getCurrentPrice().getValue()).isEqualTo(p.getPrice().getAmount());
	}
	
	@Test
    public void testDTOToEntity() {
		ProductDTO d = new ProductDTO(10, "Test product", new PriceDTO(new Money(Currency.getInstance("INR"), BigDecimal.ONE)));
		Product p = mapper.toEntity(d);
		assertThat(d.getId()).isEqualTo(p.getId());
		assertThat(d.getName()).isEqualTo(p.getName());
		assertThat(d.getCurrentPrice().getCurrencyCode()).isEqualTo(p.getPrice().getCurrency().getCurrencyCode());
		assertThat(d.getCurrentPrice().getValue()).isEqualTo(p.getPrice().getAmount());
	}
	
	@Test
    public void testEntityListToDTOList() {
		Product p1 = new Product(10, "Test product1", new Money(BigDecimal.ONE));
		Product p2 = new Product(10, "Test product2", new Money(BigDecimal.TEN));
		List<ProductDTO> dtos = mapper.toDto(Arrays.asList(p1, p2));
		assertThat(dtos.size()).isEqualTo(2);
	}
	
	@Test
    public void testDTOListToEntityList() {
		
	}
}
