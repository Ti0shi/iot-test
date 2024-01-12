package com.huitiemev.iot.controller.dto;

import java.util.ArrayList;
import java.util.List;

import com.huitiemev.iot.entity.GroceryList;
import com.huitiemev.iot.entity.enums.ListState;

public class GroceryListDTO {
    private String id;

    private String userId;

    private String caddieId;

    private ListState state;

    private List<ProductDTO> products;

    private Float totalPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCaddieId() {
        return caddieId;
    }

    public void setCaddieId(String caddieId) {
        this.caddieId = caddieId;
    }

    public ListState getState() {
        return state;
    }

    public void setState(ListState state) {
        this.state = state;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public GroceryListDTO(GroceryList groceryList) {
        this.id = groceryList.getId();
        this.userId = groceryList.getUserId();
        this.caddieId = groceryList.getCaddieId();
        this.state = groceryList.getState();
        this.totalPrice = groceryList.getTotalPrice();
        this.products = new ArrayList<>();
    }

    public GroceryList toEntity() {
        GroceryList groceryList = new GroceryList();
        groceryList.setId(this.id);
        groceryList.setUserId(this.userId);
        groceryList.setCaddieId(this.caddieId);
        groceryList.setState(this.state);
        groceryList.setTotalPrice(this.totalPrice);
        return groceryList;
    }

    public GroceryListDTO() {
    }
}
