package com.huitiemev.iot.service.impl;

import com.huitiemev.iot.entity.InListProduct;
import com.huitiemev.iot.entity.Product;
import com.huitiemev.iot.entity.RefProduct;
import com.huitiemev.iot.repository.InListProductRepository;
import com.huitiemev.iot.repository.ProductRepository;
import com.huitiemev.iot.repository.RefProductRepository;
import com.huitiemev.iot.service.ProductService;
import com.huitiemev.iot.service.RefProductService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DefaultProductService implements ProductService {
    private final ProductRepository productRepository;

    private final RefProductRepository refProductRepository;

    private final InListProductRepository inListProductRepository;

    @Inject
    public DefaultProductService(ProductRepository productRepository, RefProductRepository refProductRepository,
            InListProductRepository inListProductRepository) {
        this.productRepository = productRepository;
        this.refProductRepository = refProductRepository;
        this.inListProductRepository = inListProductRepository;
    }

    @Override
    @Transactional
    public void createProduct(Product product) {
        productRepository.persist(product);
    }

    @Override
    @Transactional
    public void updateProduct(Product product) {
        productRepository.getEntityManager().merge(product);
    }

    @Override
    public Product getProduct(String id, String subcategoryId) {
        return productRepository.getEntityManager()
                .createQuery("SELECT p FROM products p WHERE p.id = :id AND p.subcategoryId = :subcategoryId",
                        Product.class)
                .setParameter("id", id)
                .setParameter("subcategoryId", subcategoryId)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.getEntityManager()
                .createQuery("SELECT p FROM products p WHERE p.id = :id", Product.class)
                .setParameter("id", id)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Product> getAllProductsBySubcategory(String subcategoryId) {
        return productRepository.getEntityManager()
                .createQuery("SELECT p FROM products p WHERE p.subcategoryId = :subcategoryId", Product.class)
                .setParameter("subcategoryId", subcategoryId)
                .getResultList();
    }

    @Override
    public List<Product> getAllProducts(String supermarketId) {
        return productRepository.getEntityManager()
                .createQuery("SELECT p FROM products p WHERE p.supermarketId = :supermarketId", Product.class)
                .setParameter("supermarketId", supermarketId)
                .getResultList();
    }

    @Override
    public Integer getAvailableQuantityByProductId(String productId) {

        // get quantity of ref products with the same product id but maybe some products
        // are in lists (caddie)
        Integer quantityInSupermarket = getQuantityInSupermarketByProductId(productId);

        // get all ref products with the same product id
        List<RefProduct> refProducts = refProductRepository.getEntityManager()
                .createQuery("SELECT s FROM refProducts s WHERE s.productId = :productId", RefProduct.class)
                .setParameter("productId", productId)
                .getResultList();

        List<String> refProductIds = refProducts.stream().map(RefProduct::getId).toList();

        // get all products in caddie with the ref product id of the product
        List<InListProduct> inListProducts = inListProductRepository.getEntityManager()
                .createQuery("SELECT s FROM inListProduct s WHERE s.refProductId IN :refProductIds",
                        InListProduct.class)
                .setParameter("refProductIds", refProductIds)
                .getResultList();

        // if there is no product in caddie with the ref product id of the product,
        // return quantity in supermarket
        if (inListProducts == null) {
            return quantityInSupermarket;
        }

        // else return quantity in supermarket minus quantity in caddie
        Integer quantityInLists = inListProducts.size();
        return quantityInSupermarket - quantityInLists;

    }

    @Override
    public Integer getQuantityInSupermarketByProductId(String productId) {
        // get product to verify if product id exists
        Product product = productRepository.getEntityManager()
                .createQuery("SELECT p FROM products p WHERE p.id = :productId", Product.class)
                .setParameter("productId", productId)
                .getResultList()
                .stream().findFirst().orElse(null);

        // if product id does not exist, return 0
        if (product == null) {
            return 0;
        }

        // get all ref products with the same product id
        List<RefProduct> refProducts = refProductRepository.getEntityManager()
                .createQuery("SELECT s FROM refProducts s WHERE s.productId = :productId", RefProduct.class)
                .setParameter("productId", product.getId())
                .getResultList();

        // if there is no ref product with the same product id, return 0 because stock
        // in supermarket is 0
        if (refProducts == null) {
            return 0;
        }

        // get quantity of ref products with the same product id but maybe some products
        // are in lists (caddie)
        Integer quantityInSupermarket = refProducts.size();
        return quantityInSupermarket;

    }

    @Override
    public Product getProductByRefProductId(String refProductId) {
        // get ref product to verify if ref product id exists
        RefProduct refProduct = refProductRepository.getEntityManager()
                .createQuery("SELECT s FROM refProducts s WHERE s.id = :refProductId", RefProduct.class)
                .setParameter("refProductId", refProductId)
                .getResultList()
                .stream().findFirst().orElse(null);

        // if ref product id does not exist, return null
        if (refProduct == null) {
            return null;
        }

        // get product with the same product id
        Product product = productRepository.getEntityManager()
                .createQuery("SELECT p FROM products p WHERE p.id = :productId", Product.class)
                .setParameter("productId", refProduct.getProductId())
                .getResultList()
                .stream().findFirst().orElse(null);

        // if there is no product with the same product id, return null
        if (product == null) {
            return null;
        }

        return product;
    }
}
