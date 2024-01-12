package com.huitiemev.iot.controller.dto;

import com.huitiemev.iot.entity.Product;
import jakarta.validation.constraints.Min;

public class ProductDTO {
    private String id;

    private String label;

    private String imageUrl;

    @Min(0)
    private Float price;

    @Min(0)
    private Integer quantity;

    @Min(0)
    private Integer wantedQuantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getWantedQuantity() {
        return wantedQuantity;
    }

    public void setWantedQuantity(Integer wantedQuantity) {
        this.wantedQuantity = wantedQuantity;
    }

    public ProductDTO(Product product, Integer quantity) {
        this.id = product.getId();
        this.label = product.getLabel();
        this.imageUrl = product.getImageUrl();
        this.price = product.getPrice();
        this.quantity = quantity;
        this.wantedQuantity = null;
    }

    public Product toEntity() {
        Product product = new Product();
        product.setId(this.id);
        product.setLabel(this.label);
        product.setImageUrl(this.imageUrl);
        product.setPrice(this.price);
        return product;
    }

    public ProductDTO() {
    }
}
