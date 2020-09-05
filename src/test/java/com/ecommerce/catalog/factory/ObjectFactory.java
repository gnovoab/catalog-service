
//Namespace
package com.ecommerce.catalog.factory;

//Imports
import com.ecommerce.catalog.domain.model.Product;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;


/**
 * Factory Object generator
 */
public class ObjectFactory {


    public static Product generateSampleProduct() {
        Product product = new Product();

        //Set values
        product.setName(RandomStringUtils.randomAlphabetic(6));
        product.setPrice(BigDecimal.valueOf(Double.parseDouble(RandomStringUtils.randomNumeric(2, 3))));
        product.setSku("TEST-" + RandomStringUtils.randomAlphanumeric(6));

        return product;
    }
}
