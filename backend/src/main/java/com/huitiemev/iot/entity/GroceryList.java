package com.huitiemev.iot.entity;

import java.util.Date;

import com.huitiemev.iot.entity.enums.ListState;
import jakarta.persistence.*;

@Entity(name = "groceryList")
public class GroceryList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Date payedAt;
    private ListState state;
    private Float totalPrice;

    // relations
    private String caddieId;
    private String userId;
    public String groceryTemplateListId;

    // getters
    public String getCaddieId() {
        return caddieId;
    }

    public String getUserId() {
        return userId;
    }

    public String getGroceryTemplateListId() {
        return groceryTemplateListId;
    }

    public String getId() {
        return id;
    }

    public ListState getState() {
        return state;
    }

    public Date getPayedAt() {
        return payedAt;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    // setters
    public void setCaddieId(String caddieId) {
        this.caddieId = caddieId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setGroceryTemplateListId(String groceryTemplateListId) {
        this.groceryTemplateListId = groceryTemplateListId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState(ListState state) {
        this.state = state;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPayedAt(Date payedAt) {
        this.payedAt = payedAt;
    }
}
