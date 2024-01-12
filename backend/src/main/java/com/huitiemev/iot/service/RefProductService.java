package com.huitiemev.iot.service;

import java.util.List;

import com.huitiemev.iot.entity.RefProduct;

public interface RefProductService {

    public RefProduct getRefProductById(String id);

    public RefProduct getRefProductByTagId(String tagId);

    public List<RefProduct> getAllRefProductByProductId(String productId);

    public List<RefProduct> getAvailableRefProductsByProductId(String productId);

    public void removeRefProductByTagId(String tagId);

    public RefProduct createRefProduct(RefProduct refProduct);

}
