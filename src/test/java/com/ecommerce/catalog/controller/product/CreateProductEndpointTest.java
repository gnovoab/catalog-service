
//Namespace
package com.ecommerce.catalog.controller.product;


//Imports
import com.ecommerce.catalog.domain.model.Product;
import com.ecommerce.catalog.domain.rest.CreateProductStockResponse;
import com.ecommerce.catalog.factory.ProductFactory;
import com.ecommerce.catalog.service.StockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;


/**
 * Integration Test Class
 */
@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateProductEndpointTest {

    //Fields
    private static final String BASE_URL = "/api/v1/products";

    @Autowired
    private transient TestRestTemplate restTemplate;


    @TestConfiguration
    public static class TestConfig {
        @Bean
        @Primary
        public StockService auth0Service() {
            StockService stockService = Mockito.mock(StockService.class);
            Mockito.when(stockService.createProductStock(Mockito.any(Product.class))).thenReturn(new CreateProductStockResponse());
            Mockito.doReturn(new CreateProductStockResponse()).when(stockService).createProductStock(Mockito.any(Product.class));
            return stockService;
        }
    }

    @Test
    void saveProductOkTest() {
        //Create payload
        Product productPayload = ProductFactory.getSampleProduct();

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(productPayload, requestHeaders);

        //Invoke the API service
        ResponseEntity<Product> response = restTemplate
                .exchange(BASE_URL, HttpMethod.POST, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getName().length() > 0);
        Assertions.assertTrue(response.getBody().getPrice() > 0);
    }

    @Test
    void wrongPayload() {
        //Create payload
        Product productPayload = ProductFactory.getSampleProduct();
        productPayload.setName(null);

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(productPayload, requestHeaders);

        //Invoke the API service
        ResponseEntity<Product> response = restTemplate
                .exchange(BASE_URL, HttpMethod.POST, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
