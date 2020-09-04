
//Namespace
package com.ecommerce.catalog.service;

import com.ecommerce.catalog.domain.model.Product;
import com.ecommerce.catalog.domain.rest.CreateProductStockResponse;

public interface StockService {
    CreateProductStockResponse createProductStock(Product product);
}
