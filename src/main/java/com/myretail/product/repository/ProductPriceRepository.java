package com.myretail.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.myretail.product.domain.mongo.ProductPrice;

/**
 * Spring data Mongo repository following DDD Repository concept
 * 
 * @author mrinmoy
 *
 */
@Repository
public interface ProductPriceRepository extends MongoRepository<ProductPrice, Integer> {

}
