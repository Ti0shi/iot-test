package com.huitiemev.iot.entity;

import com.huitiemev.iot.entity.enums.CaddieState;

import jakarta.persistence.*;

@Entity(name = "caddies")
public class Caddie {

    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String label;
    private CaddieState state;

    // relations
    private String supermarketId;

    // getter
    public String getId() {
        return id;
    }

    public String getSupermarketId() {
        return supermarketId;
    }

    public CaddieState getState() {
        return state;
    }

    public String getLabel() {
        return label;
    }

    // setter
    public void setId(String id) {
        this.id = id;
    }

    public void setSupermarketId(String supermarketId) {
        this.supermarketId = supermarketId;
    }

    public void setState(CaddieState state) {
        this.state = state;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
