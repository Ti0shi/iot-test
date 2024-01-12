package com.huitiemev.iot.controller.dto;

import jakarta.validation.constraints.Min;

public class AddProductInTemplateListDTO {
    private String productId;

    @Min(0)
    private Integer wantedQuantity;

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setWantedQuantity(Integer wantedQuantity) {
        this.wantedQuantity = wantedQuantity;
    }

    public String getProductId() {
        return this.productId;
    }

    public Integer getWantedQuantity() {
        return this.wantedQuantity;
    }

    public AddProductInTemplateListDTO() {
    }

    public AddProductInTemplateListDTO(String productId, Integer wantedQuantity) {
        this.productId = productId;
        this.wantedQuantity = wantedQuantity;
    }

}
