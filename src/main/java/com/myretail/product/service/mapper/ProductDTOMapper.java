package com.myretail.product.service.mapper;

import org.springframework.stereotype.Component;

import com.myretail.product.domain.Product;
import com.myretail.product.service.dto.PriceDTO;
import com.myretail.product.service.dto.ProductDTO;

@Component
public class ProductDTOMapper implements DTOMapper<ProductDTO, Product> {

	@Override
	public Product toEntity(ProductDTO dto) {
		return new Product(dto.getId(), dto.getName(), dto.getCurrentPrice().toMoney());
	}

	@Override
	public ProductDTO toDto(Product entity) {
		return new ProductDTO(entity.getId(), entity.getName(), new PriceDTO(entity.getPrice()));
	}

}
