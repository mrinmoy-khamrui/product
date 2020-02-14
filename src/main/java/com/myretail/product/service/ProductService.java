package com.myretail.product.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.myretail.product.domain.Product;
import com.myretail.product.domain.mongo.ProductPrice;
import com.myretail.product.exception.PriceNotFoundException;
import com.myretail.product.exception.ProductNotFoundException;
import com.myretail.product.repository.ProductMetadataRepository;
import com.myretail.product.repository.ProductPriceRepository;
import com.myretail.product.service.dto.ProductDTO;
import com.myretail.product.service.mapper.ProductDTOMapper;

/**
 * Orchestrator service component
 * 
 * @author mrinmoy
 *
 */
@Service
public class ProductService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	private ProductMetadataRepository productMetadataRepository;
	private ProductPriceRepository productPriceRepository;
	private ProductDTOMapper productDTOMapper;
	
	public ProductService(ProductMetadataRepository productMetadataRepository, 
			ProductPriceRepository productPriceRepository,
			ProductDTOMapper productDTOMapper) {
		this.productMetadataRepository = Objects.requireNonNull(productMetadataRepository);
		this.productPriceRepository = Objects.requireNonNull(productPriceRepository);
		this.productDTOMapper = Objects.requireNonNull(productDTOMapper);
	}
	
	/**
	 * 
	 * 
	 * @param productId
	 * @return product details
	 * @throws ProductNotFoundException if product metadata not available in Redsky for given id
	 * @throws PriceNotFoundException if no price available for given id
	 */
	@Cacheable(value = "product")
	public ProductDTO getProduct(int productId) {
		String productName = productMetadataRepository.getProductName(productId);
		ProductPrice productPrice = productPriceRepository.findById(productId)
				.orElseThrow(PriceNotFoundException::new);
		Product product = new Product(productId, productName, productPrice.getPriceAsMoney());
		LOGGER.debug("Details for id {} is {}", productId, product);
		return productDTOMapper.toDto(product);
	}
	
	/**
	 * Updates product (only price) for given id with the one provided
	 * @param productDTO
	 */
	@CacheEvict(value = "product", key = "#productDTO.id")
	public void updateProduct(ProductDTO productDTO) {
		Product product = productDTOMapper.toEntity(productDTO);
		//Should validate if the product is really existing. Skipped for integration testing convenience
		updatePrice(product);
	}
	
	private void updatePrice(Product product) {
		ProductPrice productPrice = new ProductPrice();
		productPrice.setProductId(product.getId());
		productPrice.setCurrency(product.getPrice().getCurrency());
		productPrice.setValue(product.getPrice().getAmount());
		productPriceRepository.save(productPrice);
		
		LOGGER.info("Price for id {} updated to {}", product.getId(), product.getPrice());
	}
}
