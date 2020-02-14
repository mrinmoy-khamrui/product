package com.myretail.product.repository;

import com.myretail.product.exception.ProductNotFoundException;

public interface ProductMetadataRepository {

	/**
	 * 
	 * @param productId
	 * @return title of the product or null if product metadata not found for given
	 * product id
	 * @throws ProductNotFoundException if product metadata not found for given id
	 */
	String getProductName(int productId);
}
