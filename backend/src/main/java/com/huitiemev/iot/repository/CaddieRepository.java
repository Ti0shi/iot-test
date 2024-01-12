package com.huitiemev.iot.repository;

import com.huitiemev.iot.entity.Caddie;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CaddieRepository implements PanacheRepository<Caddie> {
}
