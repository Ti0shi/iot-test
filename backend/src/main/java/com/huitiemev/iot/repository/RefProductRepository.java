package com.huitiemev.iot.repository;

import com.huitiemev.iot.entity.RefProduct;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RefProductRepository implements PanacheRepository<RefProduct> {

}
