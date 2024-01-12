package com.huitiemev.iot.repository;

import com.huitiemev.iot.entity.InTemplateListProduct;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InTemplateListProductRepository implements PanacheRepository<InTemplateListProduct> {
}
