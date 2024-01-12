package com.huitiemev.iot.service.impl;

import com.huitiemev.iot.entity.InTemplateListProduct;
import com.huitiemev.iot.entity.Product;
import com.huitiemev.iot.repository.InTemplateListProductRepository;
import com.huitiemev.iot.service.InTemplateListProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DefaultInTemplateListProductService implements InTemplateListProductService {
    private final InTemplateListProductRepository inTemplateListProductRepository;

    @Inject
    public DefaultInTemplateListProductService(InTemplateListProductRepository inTemplateListProductRepository) {
        this.inTemplateListProductRepository = inTemplateListProductRepository;
    }

    @Override
    @Transactional
    public void createInTemplateListProduct(InTemplateListProduct product) {
        System.out.println("createInTemplateListProduct: " + product);
        inTemplateListProductRepository.persist(product);
    }

    @Override
    @Transactional
    public void deleteAllInTemplateListProduct(String templateListId) {
        inTemplateListProductRepository.delete("groceryTemplateListId", templateListId);
    }

    @Override
    public List<String> getAllInTemplateListProductId(String templateListId) {

        return inTemplateListProductRepository.find("groceryTemplateListId",
                templateListId).stream().map(InTemplateListProduct::getProductId).collect(Collectors.toList());
    }

    @Override
    public List<InTemplateListProduct> getAllInTemplateListProduct(String templateListId) {
        return inTemplateListProductRepository.getEntityManager()
                .createQuery(
                        "SELECT i FROM inTemplateListProduct i WHERE i.groceryTemplateListId = :groceryTemplateListId",
                        InTemplateListProduct.class)
                .setParameter("groceryTemplateListId", templateListId)
                .getResultList();
    }

    @Override
    @Transactional
    public void updateInTemplateListProduct(InTemplateListProduct product) {
        inTemplateListProductRepository.getEntityManager().merge(product);
    }

    @Override
    @Transactional
    public void deleteInTemplateListProduct(String inTemplateListProductId) {
        inTemplateListProductRepository.getEntityManager()
                .createQuery("delete from inTemplateListProduct s where s.id = :id")
                .setParameter("id", inTemplateListProductId)
                .executeUpdate();
    }

}
