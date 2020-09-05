
//Namespace
package com.ecommerce.catalog.client;

//Imports
import com.ecommerce.catalog.domain.rest.stock.CreateProductStockRequest;
import com.ecommerce.catalog.domain.rest.stock.CreateProductStockResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Unit Test Class
 */
@ActiveProfiles("unitTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockClientTest {

    //Fields
    @Autowired
    private transient StockClient stockClient;

    @Disabled("Do not run in local or isolated environment")
    @Test
    void createStockTest() {
        CreateProductStockRequest createProductStockRequest = new CreateProductStockRequest();
        createProductStockRequest.setProductId(Long.parseLong(RandomStringUtils.randomNumeric(6)) * -1);
        createProductStockRequest.setQuantity(Integer.valueOf(RandomStringUtils.randomNumeric(1,2)));

        CreateProductStockResponse createProductStockResponse = stockClient.createProductStock(createProductStockRequest);

        Assertions.assertEquals(createProductStockRequest.getProductId(), createProductStockResponse.getProductId());
        Assertions.assertEquals(createProductStockRequest.getQuantity(), createProductStockResponse.getQuantity());
    }
}
