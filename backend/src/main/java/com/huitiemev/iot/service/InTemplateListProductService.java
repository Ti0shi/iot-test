package com.huitiemev.iot.service;

import com.huitiemev.iot.entity.GroceryList;
import com.huitiemev.iot.entity.GroceryTemplateList;
import com.huitiemev.iot.entity.InTemplateListProduct;

import java.util.List;

public interface InTemplateListProductService {
    public void createInTemplateListProduct(InTemplateListProduct product);

    public void deleteAllInTemplateListProduct(String templateListId);

    public void updateInTemplateListProduct(InTemplateListProduct product);

    public void deleteInTemplateListProduct(String inTemplateListProductId);

    public List<String> getAllInTemplateListProductId(String templateListId);

    public List<InTemplateListProduct> getAllInTemplateListProduct(String templateListId);
}
