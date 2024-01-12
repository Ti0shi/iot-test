package com.huitiemev.iot.service;

import com.huitiemev.iot.entity.Category;

import java.util.List;

public interface CategoryService {
    // TODO NEED TO REMOVE
    public void createCategory(Category category);

    // TODO NEED TO REMOVE
    public Category getCategory(String id);

    public List<Category> getAllCategories();
}
