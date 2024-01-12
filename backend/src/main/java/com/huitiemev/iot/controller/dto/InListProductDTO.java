package com.huitiemev.iot.controller.dto;

import com.huitiemev.iot.entity.InListProduct;

public class InListProductDTO {

    private String groceryListId;

    private String productId;

    private Integer quantity;

    private String context;

    public String getGroceryListId() {
        return groceryListId;
    }

    public void setGroceryListId(String groceryListId) {
        this.groceryListId = groceryListId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public InListProductDTO(InListProduct inListProduct, String productId) {
        this.groceryListId = null;
        this.productId = productId;
        this.quantity = 1;
        this.context = inListProduct.getContext();
    }

    public InListProductDTO() {
    }
}
