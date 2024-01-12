package com.huitiemev.iot.controller.dto;

import java.util.ArrayList;

public class CreateTemplateListDTO {
    private String label;

    private ArrayList<String> productsIdList;

    public void setLabel(String label) {
        this.label = label;
    }

    public void setProductsIdList(ArrayList<String> productsIdList) {
        this.productsIdList = productsIdList;
    }

    public String getLabel() {
        return this.label;
    }

    public ArrayList<String> getProductsIdList() {
        return this.productsIdList;
    }
}
