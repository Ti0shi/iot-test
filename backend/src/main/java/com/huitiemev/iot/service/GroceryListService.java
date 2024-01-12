package com.huitiemev.iot.service;

import com.huitiemev.iot.entity.Category;
import com.huitiemev.iot.entity.GroceryList;
import com.huitiemev.iot.entity.enums.ListState;

import java.util.List;

public interface GroceryListService {
    public void createGroceryList(GroceryList groceryList);

    public void updateGroceryList(GroceryList groceryList);

    public void deleteGroceryList(String id);

    public GroceryList getGroceryListById(String id);

    // TODO NEED TO REMOVE
    public List<GroceryList> getGroceryAllListsByStateAndCaddieId(ListState state, String caddieId);

    public List<GroceryList> getGroceryAllListsByStateAndUserId(ListState state, String userId);

    // TODO NEED TO REMOVE
    public GroceryList getIfThereIsAnOpenGroceryListAssociatedWithTheUser(String userId, String caddieId);

    public List<GroceryList> getAllFinishedGroceryListsByUserId(String userId);

    public Float getPriceOfGroceryListId(String groceryListId);
}
