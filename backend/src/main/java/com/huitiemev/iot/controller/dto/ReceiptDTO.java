package com.huitiemev.iot.controller.dto;

import com.huitiemev.iot.entity.GroceryList;

import java.util.Date;

public class ReceiptDTO {
    private String id;

    private Float totalPrice;

    private Date payedAt;

    private String caddieLabel;

    private String supermarketLabel;

    public String getId() {
        return id;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public Date getPayedAt() {
        return payedAt;
    }

    public String getCaddieLabel() {
        return caddieLabel;
    }

    public String getSupermarketLabel() {
        return supermarketLabel;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPayedAt(Date payedAt) {
        this.payedAt = payedAt;
    }

    public void setCaddieLabel(String caddieLabel) {
        this.caddieLabel = caddieLabel;
    }

    public void setSupermarketLabel(String supermarketLabel) {
        this.supermarketLabel = supermarketLabel;
    }

    public ReceiptDTO(GroceryList groceryList) {
        this.id = groceryList.getId();
        this.totalPrice = groceryList.getTotalPrice();
        this.payedAt = groceryList.getPayedAt();
    }

    public ReceiptDTO() {
    }
}
