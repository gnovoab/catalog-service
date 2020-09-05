
//Namespace
package com.ecommerce.catalog.controller.product;

//Imports
import com.ecommerce.catalog.domain.model.Product;
import com.ecommerce.catalog.exception.ResourceNotFoundException;
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


/**
 * Integration Test Class
 */
@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateProductEndpoint {

    //Fields
    private static final String BASE_URL = "/api/v1/products";

    @Autowired
    private transient ProductRepository productRepository;

    @Autowired
    private transient TestRestTemplate restTemplate;

    @Test
    void updateProductOk() {
        //Prepopulate DB
        Product originalProduct = productRepository.save(ObjectFactory.generateSampleProduct());

        //Create payload
        Product productPayload = ObjectFactory.generateSampleProduct();
        productPayload.setPicture("");
        productPayload.setActive(false);

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(productPayload, requestHeaders);

        //Invoke the API service
        ResponseEntity<Product> response = restTemplate
                .exchange(BASE_URL + "/" + originalProduct.getId(), HttpMethod.PUT, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Product updatedProduct =  productRepository
                .findById(originalProduct.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Assertions.assertEquals(originalProduct.getId(), updatedProduct.getId());
        Assertions.assertEquals(originalProduct.getSku(), updatedProduct.getSku());
        Assertions.assertNotEquals(originalProduct.getName(), updatedProduct.getName());
        Assertions.assertNotEquals(originalProduct.getPrice(), updatedProduct.getPrice());
        Assertions.assertNotEquals(originalProduct.getPicture(), updatedProduct.getPicture());
        Assertions.assertNotEquals(originalProduct.getActive(), updatedProduct.getActive());
    }


    @Test
    void updateProductWrongPayload() {
        //Create payload
        Product productPayload = ObjectFactory.generateSampleProduct();
        productPayload.setName(null);
        productPayload.setPrice(null);

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(productPayload, requestHeaders);

        //Invoke the API service
        ResponseEntity<Product> response = restTemplate
                .exchange(BASE_URL + "/3", HttpMethod.PUT, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        //Invoke the API service
        response = restTemplate.exchange(BASE_URL + "/a", HttpMethod.PUT, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
