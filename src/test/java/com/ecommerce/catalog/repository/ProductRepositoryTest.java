
//Namespace
package com.ecommerce.catalog.repository;

//Imports
import com.ecommerce.catalog.domain.model.Product;
import com.ecommerce.catalog.exception.ResourceNotFoundException;
import com.ecommerce.catalog.factory.ObjectFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Integration Test Class
 */
@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductRepositoryTest {

    //Fields
    @Autowired
    private transient ProductRepository productRepository;


    @Test
    void updatePersistUpdateAndRegex() {

        //Created Date
        Product product = ObjectFactory.generateSampleProduct();
        product = productRepository.save(product);
        Assertions.assertNotNull(product.getCreatedDate());
        Assertions.assertNull(product.getLastModified());

        //Last Modified
        product.setPicture("sample");
        product = productRepository.save(product);
        Assertions.assertNotNull(product.getCreatedDate());
        Assertions.assertNotNull(product.getLastModified());

        //Not allow price lower or equal than 0
        product.setPrice(BigDecimal.valueOf(0));

        Product finalProduct = product;
        Assertions.assertThrows(TransactionSystemException.class, () -> {
            productRepository.save(finalProduct);
        });
    }
}
