package com.huitiemev.iot.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.List;

import com.huitiemev.iot.controller.dto.AddProductInTemplateListDTO;
import com.huitiemev.iot.entity.GroceryTemplateList;
import com.huitiemev.iot.entity.InTemplateListProduct;
import com.huitiemev.iot.service.GroceryTemplateListService;
import com.huitiemev.iot.service.InTemplateListProductService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/api/in_template_list")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InTemplateListController {

    private final InTemplateListProductService inTemplateListProductService;

    private final GroceryTemplateListService groceryTemplateListService;

    @Inject
    public InTemplateListController(InTemplateListProductService inTemplateListProductService,
            GroceryTemplateListService groceryTemplateListService) {
        this.inTemplateListProductService = inTemplateListProductService;
        this.groceryTemplateListService = groceryTemplateListService;
    }

    @PATCH
    @Path("/add_to_list/{templateListId}")
    public Response addProductToList(@PathParam("templateListId") String templateListId,
            @Valid AddProductInTemplateListDTO addProductInTemplateListDTO) {

        System.out.println("templateListId = " + templateListId);
        System.out.println("addProductInTemplateListDTO = " + addProductInTemplateListDTO.getProductId() + " "
                + addProductInTemplateListDTO.getWantedQuantity());

        if (addProductInTemplateListDTO.getProductId() == null
                || addProductInTemplateListDTO.getWantedQuantity() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        GroceryTemplateList groceryTemplateList = groceryTemplateListService
                .getGroceryTemplateListById(templateListId);

        if (groceryTemplateList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<InTemplateListProduct> inTemplateListProducts = inTemplateListProductService
                .getAllInTemplateListProduct(templateListId);

        // find in the list if the product is already present with filter method
        InTemplateListProduct inTemplateListProduct = inTemplateListProducts.stream()
                .filter(product -> product.getProductId().equals(addProductInTemplateListDTO.getProductId())).findAny()
                .orElse(null);

        if (inTemplateListProduct != null) {
            inTemplateListProduct.setWantedQuantity(addProductInTemplateListDTO.getWantedQuantity());
            inTemplateListProductService.updateInTemplateListProduct(inTemplateListProduct);
            return Response.ok().build();
        }

        inTemplateListProduct = new InTemplateListProduct();
        inTemplateListProduct.setGroceryTemplateListId(templateListId);
        inTemplateListProduct.setProductId(addProductInTemplateListDTO.getProductId());
        inTemplateListProduct.setWantedQuantity(addProductInTemplateListDTO.getWantedQuantity());
        inTemplateListProductService.createInTemplateListProduct(inTemplateListProduct);

        return Response.ok().build();
    }

    @DELETE
    @Path("/delete_from_list/{templateListId}/{productId}")
    public Response deleteProductFromList(@PathParam("templateListId") String templateListId,
            @PathParam("productId") String productId) {

        GroceryTemplateList groceryTemplateList = groceryTemplateListService
                .getGroceryTemplateListById(templateListId);

        if (groceryTemplateList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<InTemplateListProduct> inTemplateListProducts = inTemplateListProductService
                .getAllInTemplateListProduct(templateListId);

        InTemplateListProduct inTemplateListProduct = inTemplateListProducts.stream()
                .filter(product -> product.getProductId().equals(productId)).findAny()
                .orElse(null);

        if (inTemplateListProduct == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        inTemplateListProductService.deleteInTemplateListProduct(inTemplateListProduct.getId());

        return Response.ok().build();
    }

}