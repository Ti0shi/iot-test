package com.huitiemev.iot.service.impl;

import com.huitiemev.iot.entity.InListProduct;
import com.huitiemev.iot.repository.InListProductRepository;
import com.huitiemev.iot.service.InListProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class DefaultInListProductService implements InListProductService {
    private final InListProductRepository inListProductRepository;

    @Inject
    public DefaultInListProductService(InListProductRepository inListProductRepository) {
        this.inListProductRepository = inListProductRepository;
    }

    @Override
    @Transactional
    public void addProductToList(InListProduct product) {
        inListProductRepository.persist(product);
    }

    @Override
    public List<InListProduct> getProductsFromList(String groceryListId) {
        return inListProductRepository.find("groceryListId", groceryListId).list();
    }

    @Override
    @Transactional
    public Boolean verifyIfProductIsInListAndUpdate(InListProduct product) {
        /*
         * InListProduct inListProduct = inListProductRepository
         * .find("groceryListId = ?1 and productId = ?2", product.getGroceryListId(),
         * product.getProductId())
         * .firstResult();
         */
        InListProduct inListProduct = null;
        if (inListProduct != null) {
            if (product.getContext().equals("FROM_GROCERY")) {
                // inListProduct.setWantedQuantity(0);
                inListProduct.setContext("FROM_GROCERY");
            } else {
                // inListProduct.setQuantity(inListProduct.getQuantity() + 1);
            }
            if (0 < 0)
                // inListProduct.setWantedQuantity(inListProduct.getQuantity());

                inListProductRepository.persist(inListProduct);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void deleteInListProduct(String inListProductId) {
        inListProductRepository.getEntityManager().createQuery("delete from inListProduct s where s.id = :id")
                .setParameter("id", inListProductId)
                .executeUpdate();
    }

    @Override
    public void removeSimpleProduct(String inListProductId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeSimpleProduct'");
    }

    @Override
    @Transactional
    public void deleteProductFromListByRefProductId(String refProductId) {
        List<InListProduct> inListProducts = inListProductRepository.find("refProductId", refProductId).list();
        // delete only one with the ref product id
        inListProductRepository.getEntityManager().createQuery("delete from inListProduct s where s.id = :id")
                .setParameter("id", inListProducts.get(0).getId())
                .executeUpdate();
    }

}
