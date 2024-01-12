package com.huitiemev.iot.repository;

import com.huitiemev.iot.entity.GroceryTemplateList;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GroceryTemplateListRepository implements PanacheRepository<GroceryTemplateList> {
}
