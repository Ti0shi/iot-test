package com.huitiemev.iot.controller;

import com.huitiemev.iot.entity.Product;
import com.huitiemev.iot.controller.dto.CaddieDTO;
import com.huitiemev.iot.misc.HashMapOperations;
import com.huitiemev.iot.entity.Caddie;
import com.huitiemev.iot.misc.LevenshteinCalculator;
import com.huitiemev.iot.service.CaddieService;
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
@Path("/api/caddie")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CaddieController {

    private final CaddieService caddieService;

    public CaddieController(CaddieService caddieService) {
        this.caddieService = caddieService;
    }

    @GET
    @Path("/get_all/{supermarketId}")
    @Operation(summary = "Get all caddies from a supermarket")
    public Response getAllCaddies(@PathParam("supermarketId") String supermarketId) {
        List<Caddie> caddies = caddieService.getAllCaddies(supermarketId);
        return Response.ok(caddies.stream().map(CaddieDTO::new).toList()).build();
    }
}
