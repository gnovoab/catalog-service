
//Namespace
package com.ecommerce.catalog.controller.product;

//Imports
import com.ecommerce.catalog.domain.model.Product;
import com.ecommerce.catalog.factory.ObjectFactory;
import com.ecommerce.catalog.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;


/**
 * Integration Test Class
 */
@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteProductEndpoint {

    //Fields
    private static final String BASE_URL = "/api/v1/products";

    @Autowired
    private transient ProductRepository productRepository;

    @Autowired
    private transient TestRestTemplate restTemplate;

    @Test
    void deleteProductOk() {
        //Prepopulate DB
        Product originalProduct = productRepository.save(ObjectFactory.generateSampleProduct());

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(requestHeaders);

        //Invoke the API service
        ResponseEntity<Product> response = restTemplate
                .exchange(BASE_URL + "/" + originalProduct.getId(), HttpMethod.DELETE, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());


        Optional<Product> deletedProduct =  productRepository.findById(originalProduct.getId());

        Assertions.assertFalse(deletedProduct.isPresent());
    }


    @Test
    void deleteProductWrongPayload() {

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(requestHeaders);

        //Invoke the API service
        ResponseEntity<Product> response = restTemplate
                .exchange(BASE_URL + "/a", HttpMethod.DELETE, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
