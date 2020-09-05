
//Namespace
package com.ecommerce.catalog.controller.product;


//Imports
import com.ecommerce.catalog.domain.model.Product;
import com.ecommerce.catalog.domain.rest.product.CreateProductRequest;
import com.ecommerce.catalog.domain.rest.stock.CreateProductStockResponse;
import com.ecommerce.catalog.factory.ObjectFactory;
import com.ecommerce.catalog.service.StockService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
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
        public StockService stockService() {
            StockService stockService = Mockito.mock(StockService.class);
            Mockito.when(stockService.createProductStock(Mockito.any(Product.class), Mockito.anyInt())).thenReturn(new CreateProductStockResponse());
            Mockito.doReturn(new CreateProductStockResponse()).when(stockService).createProductStock(Mockito.any(Product.class), Mockito.anyInt());
            return stockService;
        }
    }

    @Test
    void saveProductOkTest() {
        //Create payload
        CreateProductRequest productRequestPayload = new CreateProductRequest();
        productRequestPayload.setProduct(ObjectFactory.generateSampleProduct());
        productRequestPayload.setQuantity(Integer.valueOf(RandomStringUtils.randomNumeric(1,3)));

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(productRequestPayload, requestHeaders);

        //Invoke the API service
        ResponseEntity<Product> response = restTemplate
                .exchange(BASE_URL, HttpMethod.POST, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getName().length() > 0);
        Assertions.assertTrue(response.getBody().getPrice().doubleValue() > 0);
    }

    @Test
    void wrongPayloadProduct() {
        //Create payload
        CreateProductRequest productRequestPayload = new CreateProductRequest();
        productRequestPayload.setProduct(null);
        productRequestPayload.setQuantity(Integer.valueOf(RandomStringUtils.randomNumeric(1,3)));

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(productRequestPayload, requestHeaders);

        //Invoke the API service
        ResponseEntity<Product> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void wrongPayloadQuantity() {
        //Create payload
        CreateProductRequest productRequestPayload = new CreateProductRequest();
        productRequestPayload.setProduct(ObjectFactory.generateSampleProduct());
        productRequestPayload.setQuantity(0);

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(productRequestPayload, requestHeaders);

        //Invoke the API service
        ResponseEntity<Product> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void wrongPayloadProductName() {
        //Create payload
        CreateProductRequest productRequestPayload = new CreateProductRequest();
        productRequestPayload.setProduct(ObjectFactory.generateSampleProduct());
        productRequestPayload.setQuantity(0);

        //Set the headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Create the http request
        HttpEntity<?> request = new HttpEntity<>(productRequestPayload, requestHeaders);

        //Invoke the API service
        ResponseEntity<Product> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, request,  new ParameterizedTypeReference<>() { });

        //Verify
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
