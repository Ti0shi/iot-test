package com.huitiemev.iot.controller;

import com.huitiemev.iot.controller.dto.GroceryListDTO;
import com.huitiemev.iot.controller.dto.PayDTO;
import com.huitiemev.iot.controller.dto.ProductDTO;
import com.huitiemev.iot.controller.dto.ReceiptDTO;
import com.huitiemev.iot.entity.Caddie;
import com.huitiemev.iot.entity.GroceryList;
import com.huitiemev.iot.entity.GroceryTemplateList;
import com.huitiemev.iot.entity.InListProduct;
import com.huitiemev.iot.entity.InTemplateListProduct;
import com.huitiemev.iot.entity.Product;
import com.huitiemev.iot.entity.RefProduct;
import com.huitiemev.iot.entity.Supermarket;
import com.huitiemev.iot.entity.User;
import com.huitiemev.iot.entity.enums.CaddieState;
import com.huitiemev.iot.entity.enums.ListState;
import com.huitiemev.iot.service.CaddieService;
import com.huitiemev.iot.service.GroceryListService;
import com.huitiemev.iot.service.GroceryTemplateListService;
import com.huitiemev.iot.service.InListProductService;
import com.huitiemev.iot.service.InTemplateListProductService;
import com.huitiemev.iot.service.ProductService;
import com.huitiemev.iot.service.RefProductService;
import com.huitiemev.iot.service.UserService;
import com.huitiemev.iot.service.SupermarketService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestScoped
@Path("/api/grocery_list")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroceryListController {

    private final GroceryListService groceryListService;

    private final CaddieService caddieService;

    private final SupermarketService supermarketService;

    private final UserService userService;

    private final InListProductService inListProductService;

    private final RefProductService refProductService;

    private final ProductService productService;

    private final GroceryTemplateListService groceryTemplateListService;

    private final InTemplateListProductService InTemplateListProductService;

    @Inject
    public GroceryListController(GroceryListService groceryListService, SupermarketService supermarketService,
            CaddieService caddieService, UserService userService, InListProductService inListProductService,
            RefProductService refProductService, ProductService productService,
            GroceryTemplateListService groceryTemplateListService,
            InTemplateListProductService InTemplateListProductService) {
        this.groceryListService = groceryListService;
        this.supermarketService = supermarketService;
        this.caddieService = caddieService;
        this.userService = userService;
        this.inListProductService = inListProductService;
        this.refProductService = refProductService;
        this.productService = productService;
        this.groceryTemplateListService = groceryTemplateListService;
        this.InTemplateListProductService = InTemplateListProductService;
    }

    public List<Product> getProductsFromGroceryList(String groceryListId) {
        List<InListProduct> inListProducts = inListProductService
                .getProductsFromList(groceryListId);

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

        return products;
    }

    public List<ProductDTO> getProductDTOsFromProductsAndGroceryTemplateListId(List<Product> products,
            String groceryTemplateListId) {

        List<InTemplateListProduct> inTemplateListProducts = InTemplateListProductService
                .getAllInTemplateListProduct(groceryTemplateListId);

        System.out.println("inTemplateListProducts = " + inTemplateListProducts);

        List<ProductDTO> productDTOS = new ArrayList<>();

        for (InTemplateListProduct inTemplateListProduct : inTemplateListProducts) {
            Product product = productService.getProductById(inTemplateListProduct.getProductId());
            ProductDTO productDTO = new ProductDTO(product, 0);
            productDTO.setWantedQuantity(inTemplateListProduct.getWantedQuantity());
            productDTOS.add(productDTO);
        }

        for (Product product : products) {
            // check if product is already in list
            ProductDTO findedProductDTO = productDTOS.stream()
                    .filter(productDTO -> productDTO.getId().equals(product.getId())).findFirst().orElse(null);
            if (findedProductDTO != null) {
                findedProductDTO.setQuantity(findedProductDTO.getQuantity() + 1);
                continue;
            }

            ProductDTO productDTO = new ProductDTO(product, 1);
            productDTOS.add(productDTO);
        }

        return productDTOS;
    }

    @GET
    @Path("/get_by_userid/{userId}")
    public Response getCurrentGroceryListByUserId(
            @PathParam("userId") String userId) {
        List<GroceryList> groceryLists = groceryListService
                .getGroceryAllListsByStateAndUserId(ListState.IN_USE, userId);

        if (groceryLists == null || groceryLists.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        GroceryList groceryList = groceryLists.get(0);

        List<Product> products = getProductsFromGroceryList(groceryList.getId());

        Float price = groceryListService.getPriceOfGroceryListId(groceryList.getId());

        GroceryListDTO groceryListDTO = new GroceryListDTO(groceryList);
        groceryListDTO.setTotalPrice(price);

        List<ProductDTO> productDTOS = getProductDTOsFromProductsAndGroceryTemplateListId(products,
                groceryList.getGroceryTemplateListId());

        groceryListDTO.setProducts(productDTOS);

        return Response.ok(groceryListDTO).build();
    }

    @GET
    @Path("/get_by_caddieid/{caddieId}")
    public Response getCurrentGroceryListByCaddieId(
            @PathParam("caddieId") String caddieId) {
        List<GroceryList> groceryLists = groceryListService
                .getGroceryAllListsByStateAndCaddieId(ListState.IN_USE, caddieId);

        if (groceryLists == null || groceryLists.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        GroceryList groceryList = groceryLists.get(0);

        List<Product> products = getProductsFromGroceryList(groceryList.getId());

        Float price = groceryListService.getPriceOfGroceryListId(groceryList.getId());

        GroceryListDTO groceryListDTO = new GroceryListDTO(groceryList);
        groceryListDTO.setTotalPrice(price);

        List<ProductDTO> productDTOS = getProductDTOsFromProductsAndGroceryTemplateListId(products,
                groceryList.getGroceryTemplateListId());

        groceryListDTO.setProducts(productDTOS);

        return Response.ok(groceryListDTO).build();
    }

    @GET
    @Path("/create/{userId}/{caddieId}")
    public Response createGroceryList(@PathParam("userId") String userId, @PathParam("caddieId") String caddieId) {
        List<GroceryList> alreadyQm = groceryListService
                .getGroceryAllListsByStateAndUserId(ListState.IN_USE, userId);
        System.out.println(alreadyQm);
        if (alreadyQm != null && !alreadyQm.isEmpty()) {
            return Response.ok(new GroceryListDTO(alreadyQm.get(0))).build();
        }

        groceryListService.getGroceryAllListsByStateAndUserId(ListState.IN_USE, userId).forEach(groceryList -> {
            groceryListService.deleteGroceryList(groceryList.getId());
        });

        Caddie caddie = caddieService.getCaddieById(caddieId);
        if (caddie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (caddie.getState() != CaddieState.FREE) {
            System.out.println("caddie = " + caddie.getState());
            return Response.status(Response.Status.CONFLICT).build();
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        GroceryList groceryList = new GroceryList();
        groceryList.setUserId(user.getId());
        groceryList.setCaddieId(caddie.getId());
        groceryList.setState(ListState.IN_USE);

        System.out.println("caddie = " + caddie.getState());

        caddie.setState(CaddieState.IN_USE);
        System.out.println("caddie = " + caddie);

        groceryListService.createGroceryList(groceryList);
        caddieService.updateCaddie(caddie);

        return Response.ok(new GroceryListDTO(groceryList)).build();
    }

    @POST
    @Path("/pay/{groceryListId}")
    public Response pay(@PathParam("groceryListId") String groceryListId, @Valid PayDTO payDTO) {
        GroceryList groceryList = groceryListService.getGroceryListById(groceryListId);
        if (groceryList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        System.out.println("groceryList = " + groceryList.getId());

        Float price = groceryListService.getPriceOfGroceryListId(groceryList.getId());

        if (price == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Float amount = payDTO.getAmount();

        if (!price.equals(amount)) {
            System.out.println("price = " + price + " totalPrice = " +  amount);
            return Response.status(Response.Status.CONFLICT).build();
        }

        groceryList.setState(ListState.FINISHED);
        groceryList.setTotalPrice(price);
        Date timeNow = new Date();
        groceryList.setPayedAt(timeNow);

        Caddie caddie = caddieService.getCaddieById(groceryList.getCaddieId());
        if (caddie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        caddie.setState(CaddieState.FREE);

        groceryListService.updateGroceryList(groceryList);
        caddieService.updateCaddie(caddie);

        return Response.ok().build();
    }

    @GET
    @Path("/get_finished/{userId}")
    public Response getFinishedGroceryList(@PathParam("userId") String userId) {
        List<GroceryList> payedGroceryLists = groceryListService.getAllFinishedGroceryListsByUserId(userId);

        if (payedGroceryLists == null || payedGroceryLists.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        System.out.println("payedGroceryLists = " + payedGroceryLists);

        payedGroceryLists.sort((o1, o2) -> o2.getPayedAt().compareTo(o1.getPayedAt()));

        List<ReceiptDTO> receipts = payedGroceryLists.stream().map(ReceiptDTO::new).toList();

        for (int i = 0; i < receipts.size(); i++) {
            Caddie caddie = caddieService.getCaddieById(payedGroceryLists.get(i).getCaddieId());
            if (caddie == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            Supermarket supermarket = supermarketService.getSupermarketById(caddie.getSupermarketId());
            if (supermarket == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            receipts.get(i).setCaddieLabel(caddie.getLabel());
            receipts.get(i).setSupermarketLabel(supermarket.getLabel());
        }

        return Response.ok(receipts).build();
    }

    @POST
    @Path("/set_template_list/{groceryListId}/{groceryTemplateListId}")
    public Response setTemplateList(@PathParam("groceryListId") String groceryListId,
            @PathParam("groceryTemplateListId") String groceryTemplateListId) {
        GroceryList groceryList = groceryListService.getGroceryListById(groceryListId);
        if (groceryList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        GroceryTemplateList groceryTemplateList = groceryTemplateListService
                .getGroceryTemplateListById(groceryTemplateListId);

        if (groceryTemplateList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (!groceryTemplateList.getUserId().equals(groceryList.getUserId())) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        groceryList.setGroceryTemplateListId(groceryTemplateList.getId());

        groceryListService.updateGroceryList(groceryList);

        return Response.ok().build();
    }

    @GET
    @Path("/get_all_product_by_grocery_id/{groceryListId}")
    public Response getAllProductByGroceryId(@PathParam("groceryListId") String groceryListId) {
        List<Product> products = getProductsFromGroceryList(groceryListId);

        GroceryList groceryList = groceryListService.getGroceryListById(groceryListId);

        System.out.println("products = " + products);
        List<ProductDTO> productDTOS = getProductDTOsFromProductsAndGroceryTemplateListId(products,
                groceryList.getGroceryTemplateListId());
        System.out.println("productDTOS = " + productDTOS);
        for (ProductDTO productDTO : productDTOS) {
            System.out.println("productDTO = " + productDTO.getWantedQuantity());
        }
        return Response.ok(productDTOS).build();
    }

    
    @POST
    @Path("/add_product_to_caddie/{caddieId}/{tagId}")
    public Response addProductToCaddie(@PathParam("caddieId") String caddieId, @PathParam("tagId") String tagId) {
        Caddie caddie = caddieService.getCaddieById(caddieId);
        if (caddie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (caddie.getState() != CaddieState.IN_USE) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        RefProduct refProduct = refProductService.getRefProductByTagId(tagId);

        if (refProduct == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<GroceryList> groceryLists = groceryListService
                .getGroceryAllListsByStateAndCaddieId(ListState.IN_USE, caddieId);
    
        if (groceryLists == null || groceryLists.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        GroceryList groceryList = groceryLists.get(0);

        InListProduct inListProduct = new InListProduct();

        inListProduct.setGroceryListId(groceryList.getId());

        inListProduct.setRefProductId(refProduct.getId());

        inListProduct.setContext("AUTO DETECTED");

        inListProductService.addProductToList(inListProduct);

        return Response.ok().build();

    }


}
