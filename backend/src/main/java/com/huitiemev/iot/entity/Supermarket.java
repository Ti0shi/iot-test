package com.huitiemev.iot.entity;

import jakarta.persistence.*;

@Entity(name = "supermarkets")
public class Supermarket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String label;

    // getters

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    // setters

    public void setId(String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
