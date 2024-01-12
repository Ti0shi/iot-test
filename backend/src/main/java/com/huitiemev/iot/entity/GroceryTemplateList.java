package com.huitiemev.iot.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "groceryTemplateList")
public class GroceryTemplateList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String label;
    private Date createdAt;

    // relations
    private String userId;

    // getters
    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return this.label;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // setters

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
