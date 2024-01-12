package com.huitiemev.iot.service.impl;

import com.huitiemev.iot.entity.Subcategory;
import com.huitiemev.iot.repository.SubcategoryRepository;
import com.huitiemev.iot.service.SubcategoryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DefaultSubcategoryService implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;

    public DefaultSubcategoryService(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    @Transactional
    public void createSubcategory(Subcategory subcategory) {
        subcategoryRepository.persist(subcategory);
    }

    @Override
    public List<Subcategory> getAllSubcategories(String categoryId) {
        return subcategoryRepository.getEntityManager()
                .createQuery("SELECT s FROM subcategories s WHERE s.categoryId = :categoryId", Subcategory.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.getEntityManager().createQuery(
                "SELECT s FROM subcategories s JOIN categories c",
                Subcategory.class)
                .getResultList();
    }
}
