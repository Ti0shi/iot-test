package com.huitiemev.iot.service.impl;

import com.huitiemev.iot.entity.Thief;
import com.huitiemev.iot.repository.ThiefRepository;
import com.huitiemev.iot.service.ThiefService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DefaultThiefService implements ThiefService {

    private final ThiefRepository thiefRepository;

    @Inject
    public DefaultThiefService(ThiefRepository thiefRepository) {
        this.thiefRepository = thiefRepository;
    }

    @Override
    @Transactional
    public void createThief(Thief thief) {
        thiefRepository.persist(thief);
    }

}
