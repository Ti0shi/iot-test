package com.huitiemev.iot.controller.dto;

import com.huitiemev.iot.entity.Subcategory;

public class SubcategoryDTO {
    private String id;

    private String label;

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SubcategoryDTO(Subcategory subcategory) {
        this.id = subcategory.getId();
        this.label = subcategory.getLabel();
    }

    public Subcategory toEntity() {
        Subcategory subcategory = new Subcategory();
        subcategory.setId(this.id);
        subcategory.setLabel(this.label);
        return subcategory;
    }

    public SubcategoryDTO() {
    }
}
