package com.huitiemev.iot.controller;

import com.huitiemev.iot.controller.dto.SubcategoryDTO;
import com.huitiemev.iot.entity.Subcategory;
import com.huitiemev.iot.service.SubcategoryService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@RequestScoped
@Path("/api/subcategory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    public SubcategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GET
    @Path("/get_all/{categoryId}")
    public Response getAllSubcategories(@PathParam("categoryId") String categoryId) {
        List<Subcategory> subcategories = subcategoryService.getAllSubcategories(categoryId);
        return Response.ok(subcategories.stream().map(SubcategoryDTO::new).toList()).build();
    }

    @GET
    @Path("/get_all")
    public Response getAllSubcategories() {
        List<Subcategory> subcategories = subcategoryService.getAllSubcategories();
        return Response.ok(subcategories.stream().map(SubcategoryDTO::new).toList()).build();
    }
}
