package com.huitiemev.iot.controller.dto;

import com.huitiemev.iot.entity.RefProduct;

public class AddRefProductDTO {

    private String productId;
    private String tagId;

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getProductId() {
        return this.productId;
    }

    public String getTagId() {
        return this.tagId;
    }

    public AddRefProductDTO() {
    }

    public AddRefProductDTO(String productId, String tagId) {
        this.productId = productId;
        this.tagId = tagId;
    }

    public AddRefProductDTO(RefProduct refProduct) {
        this.productId = refProduct.getProductId();
        this.tagId = refProduct.getTagId();
    }
    
}
