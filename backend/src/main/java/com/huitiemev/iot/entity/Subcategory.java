package com.huitiemev.iot.entity;

import java.util.Collection;
import java.util.HashSet;

import jakarta.persistence.*;

@Entity(name = "subcategories")
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String label;

    // relations
    private String categoryId;

    // getters
    public String getCategoryId() {
        return categoryId;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    // setters

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setId(String id) {
        this.id = id;
    }
}
