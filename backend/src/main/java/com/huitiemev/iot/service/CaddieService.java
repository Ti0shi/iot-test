package com.huitiemev.iot.service;

import com.huitiemev.iot.entity.Caddie;
import java.util.List;

public interface CaddieService {

    public Caddie getCaddieById(String id);

    public List<Caddie> getAllCaddies(String supermarketId);

    public void updateCaddie(Caddie caddie);
}
