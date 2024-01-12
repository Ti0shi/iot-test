package com.huitiemev.iot.entity;

import jakarta.persistence.*;

@Entity(name = "inListProduct")
public class InListProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String context;

    // relations
    private String refProductId;
    private String groceryListId;

    // getters
    public String getRefProductId() {
        return refProductId;
    }

    public String getGroceryListId() {
        return groceryListId;
    }

    public String getId() {
        return id;
    }

    public String getContext() {
        return context;
    }

    // setters
    public void setRefProductId(String refProductId) {
        this.refProductId = refProductId;
    }

    public void setGroceryListId(String groceryListId) {
        this.groceryListId = groceryListId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
