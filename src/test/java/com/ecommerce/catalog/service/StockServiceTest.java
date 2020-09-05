
//Namespace
package com.ecommerce.catalog.service;

//Imports
import com.ecommerce.catalog.client.StockClient;
import com.ecommerce.catalog.domain.rest.CreateProductStockRequest;
import com.ecommerce.catalog.domain.rest.CreateProductStockResponse;
import com.ecommerce.catalog.factory.ObjectFactory;
import com.ecommerce.catalog.service.impl.StockServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

/**
 * Unit Test Class
 */
@ActiveProfiles("unitTest")
public class StockServiceTest {

    //Fields
    @Mock
    private transient StockClient stockClient;

    @InjectMocks
    private transient StockService stockService = new StockServiceImpl();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Assertions.assertNotNull(stockService);
    }


    @Test
    void createProductStockTest() {
        //Create object to be returned
        CreateProductStockResponse createProductStockResponse = new CreateProductStockResponse();

        //Set behaviour
        Mockito.doReturn(createProductStockResponse).when(stockClient).createProductStock(Mockito.any(CreateProductStockRequest.class));

        //Execute
        stockService.createProductStock(ObjectFactory.generateSampleProduct());

        //Verify
        Mockito.verify(stockClient, Mockito.times(1)).createProductStock(Mockito.any(CreateProductStockRequest.class));
    }
}
