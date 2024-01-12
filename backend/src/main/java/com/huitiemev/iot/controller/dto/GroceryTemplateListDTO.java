package com.huitiemev.iot.controller.dto;

import com.huitiemev.iot.entity.GroceryTemplateList;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class GroceryTemplateListDTO {
    private String id;

    private Date createdAt;

    private String label;

    private List<InTemplateListDTO> inTemplateLists;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public List<InTemplateListDTO> getInTemplateLists() {
        return inTemplateLists;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setInTemplateLists(List<InTemplateListDTO> inTemplateLists) {
        this.inTemplateLists = inTemplateLists;
    }

    public GroceryTemplateListDTO(GroceryTemplateList groceryTemplateList) {
        this.id = groceryTemplateList.getId();
        this.createdAt = groceryTemplateList.getCreatedAt();
        this.label = groceryTemplateList.getLabel();
        this.inTemplateLists = null;
    }

    public GroceryTemplateList toEntity() {
        GroceryTemplateList groceryTemplateList = new GroceryTemplateList();
        groceryTemplateList.setId(this.id);
        groceryTemplateList.setCreatedAt(this.createdAt);
        groceryTemplateList.setLabel(this.label);
        return groceryTemplateList;
    }

    public GroceryTemplateListDTO() {

    }
}
