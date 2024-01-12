package com.huitiemev.iot.service.impl;

import com.huitiemev.iot.entity.GroceryTemplateList;
import com.huitiemev.iot.repository.GroceryTemplateListRepository;
import com.huitiemev.iot.service.GroceryTemplateListService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DefaultGroceryTemplateListService implements GroceryTemplateListService {
    private final GroceryTemplateListRepository groceryTemplateListRepository;

    @Inject
    public DefaultGroceryTemplateListService(GroceryTemplateListRepository groceryTemplateListRepository) {
        this.groceryTemplateListRepository = groceryTemplateListRepository;
    }

    @Override
    @Transactional
    public void createGroceryTemplateList(GroceryTemplateList groceryTemplateList) {
        groceryTemplateListRepository.persist(groceryTemplateList);
    }

    @Override
    @Transactional
    public void deleteGroceryTemplateList(String id) {
        groceryTemplateListRepository.getEntityManager()
                .createQuery("delete from groceryTemplateList s where s.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<GroceryTemplateList> getAllGroceryTemplateList(String userId) {
        return groceryTemplateListRepository.list("userId", userId);
    }

    @Override
    public GroceryTemplateList getGroceryTemplateListById(String id) {
        return groceryTemplateListRepository.getEntityManager().find(GroceryTemplateList.class, id);
    }
}
