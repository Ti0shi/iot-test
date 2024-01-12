package com.huitiemev.iot.service.impl;

import com.huitiemev.iot.entity.GroceryList;
import com.huitiemev.iot.entity.InListProduct;
import com.huitiemev.iot.entity.Product;
import com.huitiemev.iot.entity.RefProduct;
import com.huitiemev.iot.entity.enums.ListState;
import com.huitiemev.iot.repository.GroceryListRepository;
import com.huitiemev.iot.service.GroceryListService;
import com.huitiemev.iot.service.InListProductService;
import com.huitiemev.iot.service.ProductService;
import com.huitiemev.iot.service.RefProductService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DefaultGroceryListService implements GroceryListService {
    private final GroceryListRepository groceryListRepository;

    private final InListProductService inListProductService;

    private final RefProductService refProductService;

    private final ProductService productService;

    @Inject
    public DefaultGroceryListService(GroceryListRepository groceryListRepository,
            InListProductService inListProductService,
            RefProductService refProductService, ProductService productService) {
        this.groceryListRepository = groceryListRepository;
        this.inListProductService = inListProductService;
        this.refProductService = refProductService;
        this.productService = productService;
    }

    @Override
    @Transactional
    public void createGroceryList(GroceryList groceryList) {
        groceryListRepository.persist(groceryList);
    }

    @Override
    @Transactional
    public void updateGroceryList(GroceryList groceryList) {
        groceryListRepository.getEntityManager().merge(groceryList);
    }

    @Override
    @Transactional
    public void deleteGroceryList(String id) {
        groceryListRepository.getEntityManager().createQuery("DELETE FROM groceryList s WHERE s.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public GroceryList getGroceryListById(String id) {
        return groceryListRepository.getEntityManager()
                .createQuery("SELECT s FROM groceryList s WHERE s.id = :id", GroceryList.class)
                .setParameter("id", id)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<GroceryList> getGroceryAllListsByStateAndCaddieId(ListState state, String caddieId) {
        return groceryListRepository.find("state = ?1 and caddieId = ?2", state, caddieId).list();
    }

    @Override
    public List<GroceryList> getGroceryAllListsByStateAndUserId(ListState state, String userId) {
        return groceryListRepository.find("state = ?1 and userId = ?2", state, userId).list();
    }

    public GroceryList getIfThereIsAnOpenGroceryListAssociatedWithTheUserAndTheCaddie(String userId, String caddieId) {
        List<GroceryList> onGoingGroceryLists = groceryListRepository
                .find("state = ?1 and userId = ?2 and caddieId = ?3", ListState.IN_USE, userId, caddieId).list();

        if (onGoingGroceryLists.size() == 1)
            return onGoingGroceryLists.get(0);

        return null;
    }

    @Override
    public List<GroceryList> getAllFinishedGroceryListsByUserId(String userId) {
        return groceryListRepository.find("state = ?1 and userId = ?2", ListState.FINISHED, userId).list();
    }

    @Override
    public GroceryList getIfThereIsAnOpenGroceryListAssociatedWithTheUser(String userId, String caddieId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'getIfThereIsAnOpenGroceryListAssociatedWithTheUser'");
    }

    @Override
    public Float getPriceOfGroceryListId(String groceryListId) {
        GroceryList groceryList = getGroceryListById(groceryListId);

        System.out.println("groceryList = " + groceryList);

        List<InListProduct> inListProducts = inListProductService
                .getProductsFromList(groceryList.getId());

        List<RefProduct> refProducts = new ArrayList<>();

        for (InListProduct inListProduct : inListProducts) {
            RefProduct refProduct = refProductService.getRefProductById(inListProduct.getRefProductId());
            refProducts.add(refProduct);
        }

        List<Product> products = new ArrayList<>();

        for (RefProduct refProduct : refProducts) {
            Product product = productService.getProductById(refProduct.getProductId());
            products.add(product);
        }

        Float price = 0f;

        for (Product product : products) {
            price += product.getPrice();
        }

        return price;
    }
}
