package com.huitiemev.iot.service.impl;

import com.huitiemev.iot.entity.Category;
import com.huitiemev.iot.repository.CategoryRepository;
import com.huitiemev.iot.service.CategoryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DefaultCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Inject
    public DefaultCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void createCategory(Category category) {
        categoryRepository.persist(category);
    }

    @Override
    public Category getCategory(String id) {
        return categoryRepository.getEntityManager()
                .createQuery("SELECT c FROM categories c WHERE c.id = :id", Category.class)
                .setParameter("id", id)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.getEntityManager()
                .createQuery("SELECT c FROM categories c", Category.class)
                .getResultList();
    }
}
