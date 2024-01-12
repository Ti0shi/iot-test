package com.huitiemev.iot.controller.dto;

import com.huitiemev.iot.entity.Supermarket;

import java.util.List;

public class SupermarketDTO {
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SupermarketDTO(Supermarket supermarket) {
        this.label = supermarket.getLabel();
    }

    public Supermarket toEntity() {
        Supermarket supermarket = new Supermarket();
        supermarket.setLabel(this.label);
        return supermarket;
    }

    public SupermarketDTO() {
    }
}
