package com.huitiemev.iot.repository;

import com.huitiemev.iot.entity.Supermarket;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SupermarketRepository implements PanacheRepository<Supermarket> {
}
