package com.huitiemev.iot.entity;

import jakarta.persistence.*;

@Entity(name = "refProducts")
public class RefProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String tagId;

    // relations
    private String productId;

    // getters
    public String getProductId() {
        return productId;
    }

    public String getId() {
        return id;
    }

    public String getTagId() {
        return tagId;
    }

    // setters

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
