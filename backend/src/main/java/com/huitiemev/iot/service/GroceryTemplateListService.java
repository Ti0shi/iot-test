package com.huitiemev.iot.service;

import com.huitiemev.iot.entity.GroceryTemplateList;

import java.util.List;

public interface GroceryTemplateListService {
    public void createGroceryTemplateList(GroceryTemplateList groceryTemplateList);

    public void deleteGroceryTemplateList(String id);

    public GroceryTemplateList getGroceryTemplateListById(String id);

    public List<GroceryTemplateList> getAllGroceryTemplateList(String userId);
}
