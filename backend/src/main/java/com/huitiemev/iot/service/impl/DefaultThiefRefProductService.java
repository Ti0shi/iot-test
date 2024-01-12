package com.huitiemev.iot.service.impl;

import com.huitiemev.iot.entity.ThiefRefProduct;
import com.huitiemev.iot.repository.ThiefRefProductRepository;
import com.huitiemev.iot.service.ThiefRefProductService;
import jakarta.transaction.Transactional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DefaultThiefRefProductService implements ThiefRefProductService {

    private final ThiefRefProductRepository thiefRefProductRepository;

    @Inject
    public DefaultThiefRefProductService(ThiefRefProductRepository thiefRefProductRepository) {
        this.thiefRefProductRepository = thiefRefProductRepository;
    }

    @Override
    @Transactional
    public void createThiefRefProduct(ThiefRefProduct thiefRefProduct) {
        thiefRefProductRepository.persist(thiefRefProduct);
    }

}
