
//Namespace
package com.ecommerce.catalog.service;

import com.ecommerce.catalog.domain.model.Product;

public interface ProductService {
    Iterable<Product> fetchActiveProducts();
    Iterable<Product> fetchProducts();
    Product findProduct(long id);
    Product save(Product product);
    void delete(long id);
}
