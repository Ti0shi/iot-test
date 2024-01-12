package com.huitiemev.iot.service;

import com.huitiemev.iot.entity.Product;

import java.util.List;

public interface ProductService {

    // TODO NEED TO REMOVE
    public void createProduct(Product product);

    public void updateProduct(Product product);

    public Product getProduct(String id, String subcategoryId);

    public Product getProductById(String id);

    public Product getProductByRefProductId(String refProductId);

    public List<Product> getAllProductsBySubcategory(String subcategoryId);

    public List<Product> getAllProducts(String supermarketId);

    public Integer getAvailableQuantityByProductId(String productId);

    public Integer getQuantityInSupermarketByProductId(String productId);
}
