
//Namespace
package com.ecommerce.catalog.service.impl;

//Imports
import com.ecommerce.catalog.domain.model.Product;
import com.ecommerce.catalog.exception.ResourceNotFoundException;
import com.ecommerce.catalog.repository.ProductRepository;
import com.ecommerce.catalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Class hat handles operations regarding products
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private transient ProductRepository productRepository;


    @Override
    public Iterable<Product> fetchActiveProducts() {
        return productRepository.findByActiveTrue();
    }

    @Override
    public Iterable<Product> fetchProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProduct(long id) {
        return  productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
