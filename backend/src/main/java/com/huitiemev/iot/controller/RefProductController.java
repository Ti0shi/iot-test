package com.huitiemev.iot.controller;

import com.huitiemev.iot.entity.Product;
import com.huitiemev.iot.entity.RefProduct;
import com.huitiemev.iot.entity.Supermarket;
import com.huitiemev.iot.controller.dto.AddRefProductDTO;
import com.huitiemev.iot.controller.dto.CaddieDTO;
import com.huitiemev.iot.misc.HashMapOperations;
import com.huitiemev.iot.entity.Caddie;
import com.huitiemev.iot.misc.LevenshteinCalculator;
import com.huitiemev.iot.service.CaddieService;
import com.huitiemev.iot.service.GroceryListService;
import com.huitiemev.iot.service.ProductService;
import com.huitiemev.iot.service.RefProductService;
import com.huitiemev.iot.service.SupermarketService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.ceil;
import static java.lang.Math.min;

@RequestScoped
@Path("/api/ref_product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RefProductController {
    private final ProductService productService;

    private final RefProductService refProductService;

    private final SupermarketService supermarketService;

    public RefProductController(RefProductService refProductService, ProductService productService,
            SupermarketService supermarketService) {
        this.productService = productService;
        this.refProductService = refProductService;
        this.supermarketService = supermarketService;
    }

    @POST
    @Path("/add_ref_product")
    public Response addRefProduct(@Valid AddRefProductDTO addRefProductDTO) {
        String productId = addRefProductDTO.getProductId();
        String tagId = addRefProductDTO.getTagId();
        if (productId == null || tagId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Product product = productService.getProductById(productId);
        if (product == null) {
            System.out.println("addRefProduct: " + productId + " " + tagId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        RefProduct refProduct = refProductService.getRefProductByTagId(tagId);
        if (refProduct != null) {
            System.out.println("addRefProduct: " + productId + " " + tagId);
            return Response.status(Response.Status.CONFLICT).build();
        }

        RefProduct newRefProduct = new RefProduct();
        newRefProduct.setProductId(productId);
        newRefProduct.setTagId(tagId);
        refProductService.createRefProduct(newRefProduct);

        return Response.ok().build();
    }

    @GET
    @Path("/get_all_available_ref_product/{productId}")
    public Response getAllAvailableRefProduct(@PathParam("productId") String productId) {
        Product product = productService.getProductById(productId);
        System.out.println("getAllAvailableRefProduct: " + productId);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<RefProduct> refProducts = refProductService.getAvailableRefProductsByProductId(product.getId());

        return Response.ok(refProducts.stream().map(AddRefProductDTO::new).toList()).build();

    }

    @GET
    @Path("/get_all_ref_product/{supermarketId}")
    public Response getAllRefProduct(@PathParam("supermarketId") String supermarketId) {
        Supermarket supermarket = supermarketService.getSupermarketById(supermarketId);
        if (supermarket == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Product> supermarketProducts = productService.getAllProducts(supermarketId);

        List<RefProduct> refProducts = new ArrayList<>();
        for (Product product : supermarketProducts) {
            refProducts.addAll(refProductService.getAllRefProductByProductId(product.getId()));
        }

        return Response.ok(refProducts).build();

    }
}
