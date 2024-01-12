package com.huitiemev.iot.controller.dto;

public class InTemplateListDTO {
    private String id;
    private Integer wantedQuantity;
    private String label;

    // getters

    public String getId() {
        return id;
    }

    public Integer getWantedQuantity() {
        return wantedQuantity;
    }

    public String getLabel() {
        return label;
    }

    // setters

    public void setId(String id) {
        this.id = id;
    }

    public void setWantedQuantity(Integer wantedQuantity) {
        this.wantedQuantity = wantedQuantity;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // constructors

    public InTemplateListDTO() {
    }

    public InTemplateListDTO(String id, Integer wantedQuantity, String label) {
        this.id = id;
        this.wantedQuantity = wantedQuantity;
        this.label = label;
    }
}
