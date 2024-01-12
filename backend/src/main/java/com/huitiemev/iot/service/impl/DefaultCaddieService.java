package com.huitiemev.iot.service.impl;

import com.huitiemev.iot.entity.Caddie;
import com.huitiemev.iot.repository.CaddieRepository;
import com.huitiemev.iot.service.CaddieService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DefaultCaddieService implements CaddieService {
    private final CaddieRepository caddieRepository;

    @Inject
    public DefaultCaddieService(CaddieRepository caddieRepository) {
        this.caddieRepository = caddieRepository;
    }

    @Override
    public Caddie getCaddieById(String id) {
        return caddieRepository.getEntityManager().createQuery("SELECT s FROM caddies s WHERE s.id = :id", Caddie.class)
                .setParameter("id", id)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Caddie> getAllCaddies(String supermarketId) {
        System.out.println("supermarketId = " + supermarketId);
        return caddieRepository.getEntityManager()
                .createQuery("SELECT s FROM caddies s WHERE s.supermarketId = :supermarketId", Caddie.class)
                .setParameter("supermarketId", supermarketId)
                .getResultList();
    }

    @Override
    @Transactional
    public void updateCaddie(Caddie caddie) {
        System.out.println("caddie = " + caddie.getState());
        caddieRepository.getEntityManager().merge(caddie);
    }
}
