
//Namespace
package com.ecommerce.catalog.domain.rest;

/**
 * Class that represents the payload request for AddStockProduct
 */
public class CreateProductStockResponse {
    private Long productId;
    private Integer quantity;

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
