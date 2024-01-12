package com.huitiemev.iot.service.impl;

import com.huitiemev.iot.entity.Supermarket;
import com.huitiemev.iot.repository.SupermarketRepository;
import com.huitiemev.iot.service.SupermarketService;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class DefaultSupermarketService implements SupermarketService {

    private final SupermarketRepository supermarketRepository;

    public DefaultSupermarketService(SupermarketRepository supermarketRepository) {
        this.supermarketRepository = supermarketRepository;
    }

    @Override
    public Supermarket getSupermarketById(String id) {
        return supermarketRepository.getEntityManager().find(Supermarket.class, id);
    }

    public List<Supermarket> getAllSupermarkets() {
        return supermarketRepository.listAll();
    }

    public void createSupermarket(Supermarket supermarket) {
        supermarketRepository.persist(supermarket);
    }

    public void updateSupermarket(Supermarket supermarket) {
        supermarketRepository.getEntityManager().merge(supermarket);
    }

    public void deleteSupermarket(String id) {
        supermarketRepository.getEntityManager().createQuery("DELETE FROM supermarkets s WHERE s.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
