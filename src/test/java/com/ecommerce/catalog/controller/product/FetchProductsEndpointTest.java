
//Namespace
package com.ecommerce.catalog.controller.product;

//Imports
import com.ecommerce.catalog.domain.model.Product;
import com.ecommerce.catalog.factory.ProductFactory;
import com.ecommerce.catalog.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


/**
 * Integration Test Class
 */
@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FetchProductsEndpointTest {

    //Fields
    private static final String BASE_URL = "/api/v1/products";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private transient TestRestTemplate restTemplate;


    @Test
    void fetchProductsTest() {

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(requestHeaders);

        //Invoke the API service
        ResponseEntity<List<Product>> response = restTemplate
                .exchange(BASE_URL, HttpMethod.GET, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }


    @Test
    void fetchActiveProductsTest() {

        //Get total of Rows
        long totalProductsInitial = productRepository.count();

        //Prepopulate DB
        Product product1 = ProductFactory.getSampleProduct();
        Product product2 = ProductFactory.getSampleProduct();
        product2.setActive(false);

        productRepository.save(product1);
        productRepository.save(product2);

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(requestHeaders);

        //Invoke the API service
        ResponseEntity<List<Product>> response = restTemplate
                .exchange(BASE_URL + "/active", HttpMethod.GET, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(totalProductsInitial + 1, response.getBody().size());
    }

    @Test
    void fetchProduct() {
        //Prepopulate DB
        Product dbProduct = productRepository.save(ProductFactory.getSampleProduct());

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(requestHeaders);

        //Invoke the API service
        ResponseEntity<Product> response = restTemplate
                .exchange(BASE_URL + "/" + dbProduct.getId(), HttpMethod.GET, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(dbProduct.getId(), response.getBody().getId());
        Assertions.assertEquals(dbProduct.getName(), response.getBody().getName());
        Assertions.assertEquals(dbProduct.getPrice(), response.getBody().getPrice());
        Assertions.assertEquals(dbProduct.getPicture(), response.getBody().getPicture());
        Assertions.assertEquals(dbProduct.getActive(), response.getBody().getActive());
    }

    @Test
    void fetchProductWrongPayload() {
        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(requestHeaders);

        //Invoke the API service
        ResponseEntity<Product> response = restTemplate
                .exchange(BASE_URL + "/a", HttpMethod.GET, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }
}
