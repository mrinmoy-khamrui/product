package com.myretail.product.rest.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * For offline testing
 * 
 * @author mrinmoy
 *
 */
@RestController
public class DummyProductMetadataController {

	@GetMapping(value = "/v2/pdp/tcin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> get(@PathVariable String id) {
        final String json = "{\n" +
                "\t\"product\": {\n" +
                "\t\t\"item\": {\n" +
                "\t\t\t\"tcin\": \"" + id + "\",\n" +
                "\t\t\t\"bundle_components\": {},\n" +
                "\t\t\t\"dpci\": \"058-34-0436\",\n" +
                "\t\t\t\"upc\": \"025192110306\",\n" +
                "\t\t\t\"product_description\": {\n" +
                "\t\t\t\t\"title\": \"The Big Lebowski (Blu-ray)\",\n" +
                "\t\t\t\t\"bullet_description\": [\"<B>Movie Studio:</B> Universal Studios\", \"<B>Movie Genre:</B> Comedy\", \"<B>Software Format:</B> Blu-ray\"]\n" +
                "\t\t\t}" +
                "\t\t}" +
                "\t}" +
                "}";
        return ResponseEntity.ok(json);
    }
}
