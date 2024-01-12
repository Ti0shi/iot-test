package com.huitiemev.iot.controller.dto;

import com.huitiemev.iot.entity.Category;

import java.util.List;

public class CategoryDTO {
    private String id;

    private String label;

    private List<String> subCategoryIdList;

    private String imageUrl;

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

    public List<String> getSubCateogryIdList() {
        return subCategoryIdList;
    }

    public void setSubCategoryIdList(List<String> subCateogryIdList) {
        this.subCategoryIdList = subCateogryIdList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.label = category.getLabel();
        this.imageUrl = category.getImageUrl();
    }

    public Category toEntity() {
        Category category = new Category();
        category.setId(this.id);
        category.setLabel(this.label);
        category.setImageUrl(this.imageUrl);
        return category;
    }

    public CategoryDTO() {
    }
}
