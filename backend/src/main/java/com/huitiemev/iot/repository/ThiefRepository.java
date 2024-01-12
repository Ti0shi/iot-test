package com.huitiemev.iot.repository;

import com.huitiemev.iot.entity.Thief;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ThiefRepository implements PanacheRepository<Thief> {

}
