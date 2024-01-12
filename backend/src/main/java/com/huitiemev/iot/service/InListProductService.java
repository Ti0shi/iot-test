package com.huitiemev.iot.service;

import com.huitiemev.iot.entity.InListProduct;
import com.huitiemev.iot.entity.Product;

import java.util.List;

public interface InListProductService {
    public void addProductToList(InListProduct product);

    public List<InListProduct> getProductsFromList(String listId);

    // TODO NEED TO REMOVE
    public Boolean verifyIfProductIsInListAndUpdate(InListProduct product);

    // TODO NEED TO REMOVE
    public void deleteInListProduct(String inListProductId);

    public void removeSimpleProduct(String inListProductId);

    public void deleteProductFromListByRefProductId(String refProductId);
}
