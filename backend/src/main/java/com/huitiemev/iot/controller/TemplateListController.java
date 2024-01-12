package com.huitiemev.iot.controller;

import com.huitiemev.iot.controller.dto.CreateTemplateListDTO;
import com.huitiemev.iot.controller.dto.GroceryListDTO;
import com.huitiemev.iot.controller.dto.GroceryTemplateListDTO;
import com.huitiemev.iot.controller.dto.InTemplateListDTO;
import com.huitiemev.iot.entity.GroceryTemplateList;
import com.huitiemev.iot.entity.InTemplateListProduct;
import com.huitiemev.iot.entity.Product;
import com.huitiemev.iot.entity.User;
import com.huitiemev.iot.service.ProductService;
import com.huitiemev.iot.service.RefProductService;
import com.huitiemev.iot.service.UserService;
import com.huitiemev.iot.controller.dto.ProductDTO;
import com.huitiemev.iot.service.GroceryTemplateListService;
import com.huitiemev.iot.service.InTemplateListProductService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestScoped
@Path("/api/template_list")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TemplateListController {
    private final GroceryTemplateListService groceryTemplateListService;

    private final InTemplateListProductService inTemplateListProductService;

    private final ProductService productService;

    private final UserService userService;

    private final RefProductService refProductService;

    public TemplateListController(
            GroceryTemplateListService groceryTemplateListService,
            InTemplateListProductService inTemplateListProductService, ProductService productService,
            UserService userService, RefProductService refProductService) {
        this.groceryTemplateListService = groceryTemplateListService;
        this.inTemplateListProductService = inTemplateListProductService;
        this.productService = productService;
        this.userService = userService;
        this.refProductService = refProductService;
    }

    @POST
    @Path("/create/{userId}")
    public Response createTemplateList(@PathParam("userId") String userId, @Valid CreateTemplateListDTO props) {
        Date timeNow = new Date();
        GroceryTemplateList groceryTemplateList = new GroceryTemplateList();

        User user = userService.getUserById(userId);

        groceryTemplateList.setUserId(user.getId());
        groceryTemplateList.setLabel(props.getLabel());
        groceryTemplateList.setCreatedAt(timeNow);

        System.out.println("lists products: " + props.getProductsIdList());
        groceryTemplateListService.createGroceryTemplateList(groceryTemplateList);

        for (String productId : props.getProductsIdList()) {
            InTemplateListProduct inTemplateListProduct = new InTemplateListProduct();
            // inTemplateListProduct.setGroceryTemplateListId(groceryTemplateList.getId());
            Product product = productService.getProductById(productId);
            if (product == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            inTemplateListProduct.setProductId(product.getId());
            System.out.println("product id: " + product.getId());

            inTemplateListProduct.setGroceryTemplateListId(groceryTemplateList.getId());
            System.out.println("grocery template list id: " + groceryTemplateList.getId());
            inTemplateListProduct.setWantedQuantity(1);

            inTemplateListProductService.createInTemplateListProduct(inTemplateListProduct);
        }

        return Response.ok().build();
    }

    @GET
    @Path("/get_all/{userId}")
    public Response getAllTemplateList(@PathParam("userId") String userId) {
        List<GroceryTemplateList> templates = groceryTemplateListService.getAllGroceryTemplateList(userId);
        // error with the DTO, need to fix but not important
        return Response.ok(templates).build();
    }

    @DELETE
    @Path("/delete/{templateListId}")
    public Response deleteTemplateList(@PathParam("templateListId") String templateListId) {
        inTemplateListProductService.deleteAllInTemplateListProduct(templateListId);
        groceryTemplateListService.deleteGroceryTemplateList(templateListId);
        return Response.ok().build();
    }

    @GET
    @Path("/get_products_id/{templateListId}")
    public Response getProductsId(@PathParam("templateListId") String templateListId) {
        List<String> products = inTemplateListProductService.getAllInTemplateListProductId(templateListId);
        System.out.println(products);
        return Response.ok(products).build();
    }

    @GET
    @Path("/get_products/{templateListId}")
    public Response getProducts(@PathParam("templateListId") String templateListId) {
        List<String> productIds = inTemplateListProductService.getAllInTemplateListProductId(templateListId);
        List<Product> products = productIds.stream().map(productId -> {
            return productService.getProductById(productId);
        }).toList();

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
    @Path("/get_template_list_by_id/{templateListId}")
    public Response getTemplateListById(@PathParam("templateListId") String templateListId) {
        GroceryTemplateList groceryTemplateList = groceryTemplateListService.getGroceryTemplateListById(templateListId);
        if (groceryTemplateList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<InTemplateListProduct> inTemplateListProducts = inTemplateListProductService
                .getAllInTemplateListProduct(templateListId);
        
        List<InTemplateListDTO> inTemplateListDTOs = new ArrayList<>();

        for (InTemplateListProduct inTemplateListProduct : inTemplateListProducts) {
            InTemplateListDTO dto = new InTemplateListDTO();
            dto.setId(inTemplateListProduct.getId());
            Product product = productService.getProductById(inTemplateListProduct.getProductId());
            dto.setLabel(product.getLabel());
            dto.setWantedQuantity(inTemplateListProduct.getWantedQuantity());
            inTemplateListDTOs.add(dto);
        }

        GroceryTemplateListDTO groceryTemplateListDTO = new GroceryTemplateListDTO();
        groceryTemplateListDTO.setId(groceryTemplateList.getId());
        groceryTemplateListDTO.setLabel(groceryTemplateList.getLabel());
        groceryTemplateListDTO.setCreatedAt(groceryTemplateList.getCreatedAt());
        groceryTemplateListDTO.setInTemplateLists(inTemplateListDTOs);

        return Response.ok(groceryTemplateListDTO).build();
    }
}
