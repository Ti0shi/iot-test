package com.huitiemev.iot.controller;

import com.huitiemev.iot.entity.Product;
import com.huitiemev.iot.entity.RefProduct;
import com.huitiemev.iot.controller.dto.ProductDTO;
import com.huitiemev.iot.misc.HashMapOperations;
import com.huitiemev.iot.misc.LevenshteinCalculator;
import com.huitiemev.iot.service.ProductService;
import com.huitiemev.iot.service.RefProductService;

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
@Path("/api/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {
    private final ProductService productService;
    private final RefProductService refProductService;

    public ProductController(ProductService productService, RefProductService refProductService) {
        this.productService = productService;
        this.refProductService = refProductService;
    }

    @GET
    @Path("/get_by_id_and_subcategory/{subcategory}/{id}")
    public Response getProductByIdAndSubcategory(@PathParam("subcategory") String subcategory,
            @PathParam("id") String id) {
        Product product = productService.getProduct(id, subcategory);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Integer quantity = productService.getAvailableQuantityByProductId(product.getId());

        return Response.ok(new ProductDTO(product, quantity)).build();
    }

    @GET
    @Path("/get_by_id/{productId}")
    public Response getProductById(@PathParam("productId") String productId) {
        System.out.println("getProductById: " + productId);
        Product product = productService.getProductById(productId);

        Integer quantity = productService.getAvailableQuantityByProductId(product.getId());

        ProductDTO productDTO = new ProductDTO(product, quantity);

        return Response.ok(productDTO).build();
    }

    @PATCH
    @Path("/decrease_stock/{productId}")
    public Response decreaseStock(@PathParam("productId") String productId) {
        List<RefProduct> refProducts = refProductService.getAvailableRefProductsByProductId(productId);
        Integer quantity = productService.getAvailableQuantityByProductId(productId);

        if (quantity == 0) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        RefProduct refProduct = refProducts.get(0);
        refProductService.removeRefProductByTagId(refProduct.getTagId());

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PATCH
    @Path("/increase_stock/{productId}")
    public Response increaseStock(@PathParam("productId") String productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        RefProduct refProduct = new RefProduct();
        refProduct.setProductId(productId);
        Integer randomInteger = (int) (Math.random() * 1000000);
        refProduct.setTagId(randomInteger.toString());

        refProductService.createRefProduct(refProduct);

        return Response.ok().build();
    }

    // TODO to implement
    @PATCH
    @Path("/add_product/{productId}/{tagId}")
    public Response addProduct(@PathParam("productId") String productId, @PathParam("tagId") String tagId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        RefProduct refProduct = new RefProduct();
        refProduct.setProductId(productId);
        refProduct.setTagId(tagId);

        refProductService.createRefProduct(refProduct);

        return Response.ok().build();
    }

    @GET
    @Path("/get_all/{subcategoryId}")
    public Response getAllProductsByCategory(@PathParam("subcategoryId") String subcategoryId) {
        List<Product> products = productService.getAllProductsBySubcategory(subcategoryId);

        List<Integer> quantities = new ArrayList<>();
        for (Product product : products) {
            quantities.add(productService.getAvailableQuantityByProductId(product.getId()));
        }

        List<ProductDTO> productDTOs = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            productDTOs.add(new ProductDTO(products.get(i), quantities.get(i)));
        }

        return Response.ok(productDTOs).build();
    }

    @GET
    @Path("/get_all_by_supermarket/{supermarketId}")
    public Response getAllProductsBySupermarket(@PathParam("supermarketId") String supermarketId) {
        List<Product> products = productService.getAllProducts(supermarketId);

        List<Integer> quantities = new ArrayList<>();
        for (Product product : products) {
            quantities.add(productService.getAvailableQuantityByProductId(product.getId()));
        }

        List<ProductDTO> productDTOs = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            productDTOs.add(new ProductDTO(products.get(i), quantities.get(i)));
        }

        return Response.ok(productDTOs).build();
    }

    @GET
    @Path("/search_product/{supermarketId}/{subcategoryId}")
    public Response searchProduct(@PathParam("supermarketId") String supermarkedId,
            @PathParam("subcategoryId") String subcategoryId,
            @QueryParam("search") String search) {
        if (search == null || search.isBlank())
            return Response.ok(new ArrayList<>()).build();

        List<Product> products;
        if (subcategoryId.equals("0")) {
            products = productService.getAllProducts(supermarkedId);
        } else {
            products = productService.getAllProductsBySubcategory(subcategoryId);
        }

        double threshold = ceil((double) search.length() / 4);
        HashMap<ProductDTO, Integer> productMap = new HashMap<>();

        for (Product product : products) {
            int distance = LevenshteinCalculator.isMatching(search, product.getLabel());
            if (distance <= threshold) {
                Integer quantity = productService.getAvailableQuantityByProductId(product.getId());
                productMap.put(new ProductDTO(product, quantity), distance);
            }
        }

        productMap = HashMapOperations.sortHashMapByValue(productMap, true);

        List<ProductDTO> productsOrdered = new ArrayList<>(productMap.keySet());
        List<ProductDTO> closestProducts = productsOrdered.subList(0, min(20, productsOrdered.size()));

        return Response.ok(closestProducts).build();
    }

    @PATCH
    @Path("/update_product")
    @Operation(summary = "Update a product given it's id and new label, price and quantity")
    public Response updateProduct(@Valid ProductDTO productDTO) {
        Product product = productService.getProductById(productDTO.getId());
        product.setLabel(productDTO.getLabel());
        product.setPrice(productDTO.getPrice());

        Integer quantity = productService.getAvailableQuantityByProductId(product.getId());
        if (quantity != productDTO.getQuantity()) {
            if (quantity > productDTO.getQuantity()) {
                Integer diff = quantity - productDTO.getQuantity();
                for (int i = 0; i < diff; i++) {
                    decreaseStock(product.getId());
                }
            } else {
                Integer diff = productDTO.getQuantity() - quantity;
                for (int i = 0; i < diff; i++) {
                    increaseStock(product.getId());
                }
            }
        }
        product.setImageUrl(productDTO.getImageUrl());
        productService.updateProduct(product);
        return Response.ok().build();
    }
}
