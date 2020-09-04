
//Namespace
package com.ecommerce.catalog.service.impl;

//Imports
import com.ecommerce.catalog.exception.HttpClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ecommerce.catalog.client.StockClient;
import com.ecommerce.catalog.domain.model.Product;
import com.ecommerce.catalog.domain.rest.CreateProductStockRequest;
import com.ecommerce.catalog.domain.rest.CreateProductStockResponse;
import com.ecommerce.catalog.service.StockService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * Class hat handles operations regarding product stock
 */
@Service
public class StockServiceImpl implements StockService {

    //The LOG
    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);

    //Fields
    @Autowired
    private transient StockClient stockClient;


    /**
     * Create product stock
     * @param product
     * @return
     */
    @Retryable(value = {FeignException.class, RuntimeException.class}, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    @Override
    public CreateProductStockResponse createProductStock(Product product) {

        //Create payload
        CreateProductStockRequest productStockRequestPayload = new CreateProductStockRequest();
        productStockRequestPayload.setProductId(product.getId());
        productStockRequestPayload.setQuantity(0);

        //Update stock
        return stockClient.createProductStock(productStockRequestPayload);
    }

    @Recover
    public CreateProductStockResponse  createProductStockRecoveryMethod(Throwable e, Product product) {

        if (e instanceof FeignException) {
            if (((FeignException) e).status() == HttpStatus.BAD_REQUEST.value()) {
                LOGGER.error("Unable to create product stock for [{}] due Bad Request", product.getName(), e);
            }
            else if (((FeignException) e).status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                LOGGER.error("Unable to create product stock for [{}] due Internal Server Error", product.getName(), e);
            }
        }
        else if (e.getMessage().contains("netflix.client.ClientException")) {
            LOGGER.error("ClientException while creating product stock for [{}] in the Stock Management Service. Service could be DOWN",  product.getName(), e);
        }
        else {
            LOGGER.error("Unknown Exception occurred while creating product stock for [{}] in the Stock Management Service",  product.getName(), e);
        }

        throw new HttpClientException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error while login user in the Auth service", e);
    }
}
