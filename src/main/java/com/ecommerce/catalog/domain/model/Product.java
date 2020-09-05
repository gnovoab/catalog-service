
//Namespace
package com.ecommerce.catalog.domain.model;

//Imports
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
public class Product {

    //Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private UUID uuid;

    @Size(min = 2, max = 14)
    @Column(unique = true, updatable = false)
    private String sku;

    @NotBlank(message = "Product name is required.")
    @Basic(optional = false)
    private String name;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    private String picture;
    private Boolean active = true;


    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @Column(name = "last_modified")
    private Date lastModified;


    @PrePersist
    public void prePersist() {
        this.createdDate = new Date();
        this.uuid = UUID.randomUUID();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModified = new Date();
    }

    //Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreatedDate() {
        if (this.createdDate == null) {
            return null;
        }
        return new Date(this.createdDate.getTime());
    }

    public void setCreatedDate(Date createdDate) {
        if (createdDate != null) {
            this.createdDate = new Date(createdDate.getTime());
        }
    }

    public Date getLastModified() {
        if (this.lastModified == null) {
            return null;
        }
        return new Date(this.lastModified.getTime());
    }

    public void setLastModified(Date lastModified) {

        if (lastModified != null) {
            this.lastModified = new Date(lastModified.getTime());
        }
    }
}
