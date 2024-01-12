package com.huitiemev.iot.controller;

import com.huitiemev.iot.entity.GroceryList;
import com.huitiemev.iot.entity.InListProduct;
import com.huitiemev.iot.entity.Product;
import com.huitiemev.iot.entity.RefProduct;
import com.huitiemev.iot.service.InListProductService;
import com.huitiemev.iot.service.ProductService;
import com.huitiemev.iot.service.RefProductService;
import com.huitiemev.iot.controller.dto.InListProductDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Path("/api/in_list_product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InListProductController {
    private final InListProductService inListProductService;

    private final ProductService productService;

    private final RefProductService refProductService;

    public InListProductController(InListProductService inListProductService, ProductService productService,
            RefProductService refProductService) {
        this.inListProductService = inListProductService;
        this.productService = productService;
        this.refProductService = refProductService;
    }

    @POST
    @Path("/add_to_list")
    public Response addProductToList(@Valid InListProductDTO inListProductDTO) {
        if (inListProductDTO.getGroceryListId() == null || inListProductDTO.getProductId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Product product = productService.getProductById(inListProductDTO.getProductId());
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<RefProduct> refProducts = refProductService.getAvailableRefProductsByProductId(product.getId());

        Integer quantity = productService.getAvailableQuantityByProductId(product.getId());

        if (quantity < inListProductDTO.getQuantity()) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        for (int i = 0; i < inListProductDTO.getQuantity(); i++) {
            RefProduct refProduct = refProducts.get(i);
            InListProduct inListProduct = new InListProduct();
            inListProduct.setContext(inListProductDTO.getContext());
            inListProduct.setRefProductId(refProduct.getId());
            inListProduct.setGroceryListId(inListProductDTO.getGroceryListId());
            inListProductService.addProductToList(inListProduct);
        }

        return Response.ok().build();
    }

    @GET
    @Path("/get_all/{groceryListId}")
    public Response getProductsFromList(@PathParam("groceryListId") String groceryListId) {
        List<InListProduct> groceryLists = inListProductService.getProductsFromList(groceryListId);

        List<InListProductDTO> inListProductDTOS = new ArrayList<>();

        for (InListProduct inListProduct : groceryLists) {
            System.out.println("inListProduct: " + inListProduct.getRefProductId());
            Product product = productService.getProductByRefProductId(inListProduct.getRefProductId());
            InListProductDTO isAlreadyInList = inListProductDTOS.stream()
                    .filter(inListProductDTO -> inListProductDTO.getProductId()
                            .equals(product.getId()))
                    .findFirst().orElse(null);
            System.out.println("isAlreadyInList: " + isAlreadyInList);
            if (isAlreadyInList != null) {
                isAlreadyInList.setQuantity(isAlreadyInList.getQuantity() + 1);
                continue;
            }
            inListProductDTOS.add(new InListProductDTO(inListProduct, product.getId()));
        }

        return Response.ok(inListProductDTOS).build();
    }

    @DELETE
    @Path("/delete_from_list")
    public Response deleteProductFromList(@Valid InListProductDTO inListProductDTO) {

        // product need to be deleted
        Product productWouldBeDeleted = productService.getProductById(inListProductDTO.getProductId());
        if (productWouldBeDeleted == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // get all product in grocery list
        List<InListProduct> inListProducts = inListProductService
                .getProductsFromList(inListProductDTO.getGroceryListId());

        List<RefProduct> refProducts = new ArrayList<>();

        // get all ref product in grocery list by ref product id
        for (InListProduct inListProduct : inListProducts) {
            RefProduct refProduct = refProductService.getRefProductById(inListProduct.getRefProductId());
            refProducts.add(refProduct);
        }

        // get all ref product in grocery list who are the same as the product to delete
        List<RefProduct> refProductsToDelete = refProducts.stream()
                .filter(refProduct -> refProduct.getProductId().equals(productWouldBeDeleted.getId())).toList();

        // if there is not enough product to delete
        if (refProductsToDelete.size() < inListProductDTO.getQuantity()) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        // delete all ref product in grocery list who are the same as the product to
        // delete in function of quantity
        for (int i = 0; i < inListProductDTO.getQuantity(); i++) {
            RefProduct refProduct = refProductsToDelete.get(i);
            inListProductService.deleteProductFromListByRefProductId(refProduct.getId());
        }

        return Response.ok().build();
    }

    @PATCH
    @Path("/remove_simple_product/{inListProductId}")
    public Response removeSimpleProductFromList(@PathParam("inListProductId") String inListProductId) {
        inListProductService.removeSimpleProduct(inListProductId);
        return Response.ok().build();
    }
}
