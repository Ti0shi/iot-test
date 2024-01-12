package com.huitiemev.iot.service;

import com.huitiemev.iot.entity.Supermarket;

import java.util.List;

public interface SupermarketService {
    public Supermarket getSupermarketById(String id);


    public List<Supermarket> getAllSupermarkets();

    public void createSupermarket(Supermarket supermarket);

    // TODO NEED TO REMOVE
    public void updateSupermarket(Supermarket supermarket);

    // TODO NEED TO REMOVE
    public void deleteSupermarket(String id);
}
