package com.huitiemev.iot.service.impl;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema.True;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.huitiemev.iot.entity.InListProduct;
import com.huitiemev.iot.entity.RefProduct;
import com.huitiemev.iot.repository.InListProductRepository;
import com.huitiemev.iot.repository.RefProductRepository;
import com.huitiemev.iot.service.RefProductService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DefaultRefProductService implements RefProductService {

    private final RefProductRepository refProductRepository;

    private final InListProductRepository inListProductRepository;

    @Inject
    public DefaultRefProductService(RefProductRepository refProductRepository,
            InListProductRepository inListProductRepository) {
        this.refProductRepository = refProductRepository;
        this.inListProductRepository = inListProductRepository;
    }

    @Override
    public List<RefProduct> getAllRefProductByProductId(String productId) {
        return refProductRepository.getEntityManager()
                .createQuery("SELECT s FROM refProducts s WHERE s.productId = :productId", RefProduct.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    @Override
    @Transactional
    public void removeRefProductByTagId(String tagId) {
        System.out.println("removeRefProductByTagId: " + tagId);
        refProductRepository.getEntityManager().createQuery("DELETE FROM refProducts s WHERE s.tagId = :tagId")
                .setParameter("tagId", tagId)
                .executeUpdate();
    }

    @Override
    @Transactional
    public RefProduct createRefProduct(RefProduct refProduct) {
        refProductRepository.persist(refProduct);
        return refProduct;
    }

    @Override
    public RefProduct getRefProductById(String id) {
        return refProductRepository.getEntityManager()
                .createQuery("SELECT s FROM refProducts s WHERE s.id = :id", RefProduct.class)
                .setParameter("id", id)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public RefProduct getRefProductByTagId(String tagId) {
        return refProductRepository.getEntityManager()
                .createQuery("SELECT s FROM refProducts s WHERE s.tagId = :tagId", RefProduct.class)
                .setParameter("tagId", tagId)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<RefProduct> getAvailableRefProductsByProductId(String productId) {

        List<RefProduct> refProducts = refProductRepository.getEntityManager()
                .createQuery("SELECT s FROM refProducts s WHERE s.productId = :productId", RefProduct.class)
                .setParameter("productId", productId)
                .getResultList();

        refProducts.removeIf(refProduct -> {
            InListProduct inListProduct = inListProductRepository.getEntityManager()
                    .createQuery("SELECT s FROM inListProduct s WHERE s.refProductId = :refProductId",
                            InListProduct.class)
                    .setParameter("refProductId", refProduct.getId())
                    .getResultList()
                    .stream().findFirst().orElse(null);
            return inListProduct != null;
        });

        return refProducts;
    }
}
