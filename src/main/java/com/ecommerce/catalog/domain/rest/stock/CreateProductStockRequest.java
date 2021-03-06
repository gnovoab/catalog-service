
//Namespace
package com.ecommerce.catalog.domain.rest.stock;

/**
 * Class that represents the payload request for AddStockProduct
 */
public class CreateProductStockRequest {
    private Long productId;
    private Integer quantity;


    //Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
