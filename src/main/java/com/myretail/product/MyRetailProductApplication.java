package com.myretail.product;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.myretail.product.domain.mongo.ProductPrice;
import com.myretail.product.repository.ProductPriceRepository;

@SpringBootApplication
public class MyRetailProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRetailProductApplication.class, args);
	}

//	@Bean
//    CommandLineRunner init(ProductPriceRepository productPriceRepository) {
//        return args -> populateTestData(productPriceRepository);
//    }
//
//	private void populateTestData(ProductPriceRepository productPriceRepository) {
//		productPriceRepository.deleteAll();
//		productPriceRepository.saveAll(Arrays.asList(
//											new ProductPrice(13860427, "25.99", "USD"),
//											new ProductPrice(13860428, "55.99", "USD"),
//											new ProductPrice(13860429, "99.99", "USD"),
//											new ProductPrice(13860430, "255.99", "USD")
//										));
//	}
}
