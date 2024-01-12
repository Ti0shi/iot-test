package com.huitiemev.iot.repository;

import com.huitiemev.iot.entity.InListProduct;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InListProductRepository implements PanacheRepository<InListProduct> {
}
