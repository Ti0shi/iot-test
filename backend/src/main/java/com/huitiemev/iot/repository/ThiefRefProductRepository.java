package com.huitiemev.iot.repository;

import com.huitiemev.iot.entity.ThiefRefProduct;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ThiefRefProductRepository implements PanacheRepository<ThiefRefProduct> {

}
