package com.huitiemev.iot.controller;

import com.huitiemev.iot.controller.dto.ConnexionDTO;
import com.huitiemev.iot.controller.dto.CreateUserDTO;
import com.huitiemev.iot.controller.dto.GateDTO;
import com.huitiemev.iot.controller.dto.UserDTO;
import com.huitiemev.iot.entity.Caddie;
import com.huitiemev.iot.entity.GroceryList;
import com.huitiemev.iot.entity.InListProduct;
import com.huitiemev.iot.entity.RefProduct;
import com.huitiemev.iot.entity.Thief;
import com.huitiemev.iot.entity.ThiefRefProduct;
import com.huitiemev.iot.entity.User;
import com.huitiemev.iot.entity.enums.ListState;
import com.huitiemev.iot.service.CaddieService;
import com.huitiemev.iot.service.GroceryListService;
import com.huitiemev.iot.service.InListProductService;
import com.huitiemev.iot.service.RefProductService;
import com.huitiemev.iot.service.ThiefRefProductService;
import com.huitiemev.iot.service.ThiefService;
import com.huitiemev.iot.service.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.joda.time.DateTimeZone;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.MediaType;

@RequestScoped
@Path("/api/gate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class GateController {

    private final CaddieService caddieService;

    private final GroceryListService groceryListService;

    private final InListProductService inListProductService;

    private final RefProductService refProductService;

    private final ThiefService thiefService;

    private final ThiefRefProductService thiefRefProductService;

    @Inject
    public GateController(CaddieService caddieService, GroceryListService groceryListService,
            InListProductService inListProductService, RefProductService refProductService, ThiefService thiefService,
            ThiefRefProductService thiefRefProductService) {
        this.caddieService = caddieService;
        this.groceryListService = groceryListService;
        this.inListProductService = inListProductService;
        this.refProductService = refProductService;
        this.thiefService = thiefService;
        this.thiefRefProductService = thiefRefProductService;
    }

    @POST
    @Path("/pass_gate/{caddieId}")
    public Response passGate(@PathParam("caddieId") String caddieId, @Valid GateDTO gateDTO) {
        if (caddieId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Caddie caddie = caddieService.getCaddieById(caddieId);

        if (caddie == null) {
            System.err.println("caddie not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<GroceryList> openedGroceryLists = groceryListService.getGroceryAllListsByStateAndCaddieId(ListState.IN_USE,
                caddie.getId());

        if (openedGroceryLists.size() != 0) {
            System.out.println("no paiement find for caddie " + caddie.getId());
            try {
                createThief(gateDTO.getrefProductIds(), caddieId);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
            return Response.ok().build();
        }

        List<GroceryList> closedGroceryLists = groceryListService.getGroceryAllListsByStateAndCaddieId(
                ListState.FINISHED,
                caddie.getId());

        if (closedGroceryLists.size() == 0) {
            System.err.println("no closed grocery list found");
            try {
                createThief(gateDTO.getrefProductIds(), caddieId);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
            return Response.ok().build();
        }

        closedGroceryLists.sort((o1, o2) -> o1.getPayedAt().compareTo(o2.getPayedAt()));

        GroceryList groceryList = closedGroceryLists.get(0);

        List<InListProduct> inListProducts = inListProductService.getProductsFromList(groceryList.getId());

        if (inListProducts.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<String> refProductIds = gateDTO.getrefProductIds();

        List<String> stealRefProductIds = new ArrayList<>();

        System.out.println(refProductIds);

        for (String refProductId : refProductIds) {
            List<InListProduct> inListProductsFiltered = inListProducts.stream()
                    .filter(inListProduct -> inListProduct.getRefProductId().equals(refProductId)).toList();

            if (inListProductsFiltered.size() == 0) {
                System.out.println("RefProduct: " + refProductId + " not found");
                stealRefProductIds.add(refProductId);
            }
        }

        if (stealRefProductIds.size() == 0) {
            System.out.println("no thief found");
            return Response.ok().build();
        }

        try {
            createThief(stealRefProductIds, caddieId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        System.out.println(stealRefProductIds);
        return Response.ok().build();
    }

    void createThief(List<String> stealRefProducIds, String caddieId) {

        Thief thief = new Thief();
        thief.setCaddieId(caddieId);
        Date date = new Date();
        thief.setCreatedAt(date);

        thiefService.createThief(thief);
        System.out.println("Thief: " + thief.getId() + " created at " + thief.getCreatedAt());
        System.out.println(stealRefProducIds);
        for (String stealRefProducId : stealRefProducIds) {
            System.out.println("RefProduct: " + stealRefProducId + " found");
            RefProduct refProduct = refProductService.getRefProductById(stealRefProducId);
            if (refProduct == null) {
                System.err.println("RefProduct: " + stealRefProducId + " not found");
                continue;
            }
            System.out.println("RefProduct: " + refProduct.getId() + " found");
            ThiefRefProduct thiefRefProduct = new ThiefRefProduct();
            thiefRefProduct.setRefProductId(refProduct.getId());
            thiefRefProduct.setThiefId(thief.getId());
            thiefRefProductService.createThiefRefProduct(thiefRefProduct);
            System.out.println("ThiefRefProduct: " + thiefRefProduct.getId() + " thiefId: "
                    + thiefRefProduct.getThiefId() + " refProductId: " + thiefRefProduct.getRefProductId());
        }

    }

}
