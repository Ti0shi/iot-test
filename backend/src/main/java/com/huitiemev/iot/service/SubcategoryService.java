package com.huitiemev.iot.service;

import com.huitiemev.iot.entity.Subcategory;

import java.util.List;

public interface SubcategoryService {
    // TODO NEED TO REMOVE
    public void createSubcategory(Subcategory subcategory);

    public List<Subcategory> getAllSubcategories(String categoryId);

    public List<Subcategory> getAllSubcategories();
}
