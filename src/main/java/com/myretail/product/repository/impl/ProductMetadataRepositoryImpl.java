package com.myretail.product.repository.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;
import com.myretail.product.exception.ProductNotFoundException;
import com.myretail.product.repository.ProductMetadataRepository;

@Repository
public class ProductMetadataRepositoryImpl implements ProductMetadataRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductMetadataRepositoryImpl.class);
	
	private static final String TITLE_JSON_PATH_STRING = "$.product.item.product_description.title";
	private static final JsonPath TITLE_JSON_PATH = JsonPath.compile(TITLE_JSON_PATH_STRING);
	
	private RestTemplate redskyRestTemplate;
	private String redskyUriTemplate;

	public ProductMetadataRepositoryImpl(RestTemplate redskyRestTemplate, @Value("${redsky.uri.template}") String redskyUriTemplate) {
		this.redskyRestTemplate = Objects.requireNonNull(redskyRestTemplate);
		this.redskyUriTemplate = Objects.requireNonNull(redskyUriTemplate);
	}
	
	@Override
	public String getProductName(int productId) {
		try { // the same could be achieved with JsonNode
			ResponseEntity<String> response = redskyRestTemplate.getForEntity(redskyUriTemplate, String.class, productId);
			LOGGER.trace("Redsky service response for id {} is status {}, BODY {}", productId, response.getStatusCode(), response.getBody());
			return TITLE_JSON_PATH.read(response.getBody());
		} catch(HttpClientErrorException.NotFound e) {
			LOGGER.warn("No product metadata found in Redsky for id {}", productId);
			throw new ProductNotFoundException();
		}
	}

}
