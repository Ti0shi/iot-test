package com.huitiemev.iot.controller;

import com.huitiemev.iot.entity.Supermarket;
import com.huitiemev.iot.service.SupermarketService;
import jakarta.inject.Inject;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/api/supermarket")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SupermarketController {
    private final SupermarketService supermarketService;

    @Inject
    public SupermarketController(SupermarketService supermarketService) {
        this.supermarketService = supermarketService;
    }

    @GET
    @Path("/get_all")
    public Response getAllSupermarkets() {
        return Response.ok(supermarketService.getAllSupermarkets()).build();
    }

    @GET
    @Path("/get_by_id/{id}")
    public Response getSupermarketById(@PathParam("id") String id) {
        return Response.ok(supermarketService.getSupermarketById(id)).build();
    }

    @POST
    @Path("/create")
    public Response createSupermarket(@Valid String label) {
        Supermarket supermarket = new Supermarket();
        supermarket.setLabel(label);
        supermarketService.createSupermarket(supermarket);
        return Response.ok().build();
    }
}
