
//Namespace
package com.ecommerce.catalog.domain.rest.product;

//Namespace
import com.ecommerce.catalog.domain.model.Product;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Class that represents the request payload for create a Product
 */
public class CreateProductRequest {

    //Fields
    @NotNull
    private Product product;

    @NotNull
    @Positive
    private Integer quantity;


    //Getters and Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
