package com.ecommerce.catalog.factory;

import com.ecommerce.catalog.domain.model.Product;
import org.apache.commons.lang3.RandomStringUtils;

public class ProductFactory {


    public static Product getSampleProduct() {
        Product product = new Product();

        //Set values
        product.setName(RandomStringUtils.randomAlphabetic(6));
        product.setPrice(Double.valueOf(RandomStringUtils.randomNumeric(1, 300)));
        product.setSku("TEST-" + RandomStringUtils.randomAlphanumeric(6));

        return product;
    }
}
