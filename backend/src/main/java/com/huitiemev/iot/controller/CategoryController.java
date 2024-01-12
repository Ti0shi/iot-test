package com.huitiemev.iot.controller;

import com.huitiemev.iot.controller.dto.CategoryDTO;
import com.huitiemev.iot.entity.Category;
import com.huitiemev.iot.service.CategoryService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@RequestScoped
@Path("/api/category")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GET
    @Path("/get_all")
        public Response getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        categories.sort((o1, o2) -> o1.getLabel().compareTo(o2.getLabel()));
        return Response.ok(categories.stream().map(CategoryDTO::new).toList()).build();
    }
}
