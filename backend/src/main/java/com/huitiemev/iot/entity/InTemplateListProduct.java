package com.huitiemev.iot.entity;

import jakarta.persistence.*;

@Entity(name = "inTemplateListProduct")
public class InTemplateListProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Integer wantedQuantity;

    // relations
    private String productId;
    private String groceryTemplateListId;

    // getters
    public String getProductId() {
        return productId;
    }

    public String getId() {
        return id;
    }

    public Integer getWantedQuantity() {
        return wantedQuantity;
    }

    public String getGroceryTemplateListId() {
        return groceryTemplateListId;
    }

    // setters

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWantedQuantity(Integer wantedQuantity) {
        this.wantedQuantity = wantedQuantity;
    }

    public void setGroceryTemplateListId(String groceryTemplateListId) {
        this.groceryTemplateListId = groceryTemplateListId;
    }
}
