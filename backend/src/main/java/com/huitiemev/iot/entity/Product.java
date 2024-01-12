package com.huitiemev.iot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.util.Collection;

@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String label;
    private String imageUrl;
    @Min(0)
    private Float price;

    // relations
    private String supermarketId;
    private String subcategoryId;

    // getters
    public String getSupermarketId() {
        return supermarketId;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Float getPrice() {
        return price;
    }

    // setters
    public void setSupermarketId(String supermarketId) {
        this.supermarketId = supermarketId;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

}
