
//Namespace
package com.ecommerce.catalog.client;

// imports
import com.ecommerce.catalog.domain.rest.CreateProductStockRequest;
import com.ecommerce.catalog.domain.rest.CreateProductStockResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*i
 * Client class that stablish connection with the stock-management-system mycroservice
 */
@FeignClient(value = "stock-service")
public interface StockClient {
    @PostMapping(
            value = "/api/v1/stock",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    CreateProductStockResponse createProductStock(@RequestBody CreateProductStockRequest createProductStockRequest);
}
