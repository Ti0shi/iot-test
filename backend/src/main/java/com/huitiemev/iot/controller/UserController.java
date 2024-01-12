package com.huitiemev.iot.controller;

import com.huitiemev.iot.controller.dto.ConnexionDTO;
import com.huitiemev.iot.controller.dto.CreateUserDTO;
import com.huitiemev.iot.controller.dto.UserDTO;
import com.huitiemev.iot.entity.User;
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
import java.util.List;

@RequestScoped
@Path("/api/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("/create")
    @Operation(summary = "Create a new user given it's first name, last name, email, password and phone number")
    public Response createUser(@Valid CreateUserDTO userInfosDTO) {
        if (userInfosDTO == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (userService.getUserByEmail(userInfosDTO.getEmail()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        if (userService.getUserByPhoneNumber(userInfosDTO.getPhoneNumber()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        User user = userInfosDTO.toEntity();
        /* user.setCreatedAt(ZonedDateTime.now());
        user.setUpdatedAt(ZonedDateTime.now()); */
        userService.createUser(user);
        return Response.ok().build();
    }

    @GET
    @Path("/get_by_id/{id}")
    public Response getUserById(@PathParam("id") String id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new UserDTO(user)).build();
    }

    @GET
    @Path("/get_by_email/{email}")
    public Response getUserByEmail(@PathParam("email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new UserDTO(user)).build();
    }

    @GET
    @Path("/get_by_phone/{phoneNumber}")
    public Response getUserByPhoneNumber(@PathParam("phoneNumber") String phoneNumber) {
        User user = userService.getUserByPhoneNumber(phoneNumber);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new UserDTO(user)).build();
    }

    @GET
    @Path("/get_all")
    public Response getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<UserDTO> userDTOS = users.stream().map(UserDTO::new).toList();
        return Response.ok(userDTOS).build();
    }

    @POST
    @Path("/connect")
    public Response getUserByEmailAndPassword(@Valid ConnexionDTO connexionDTO) {
        User user = userService.getUserByEmailAndPassword(connexionDTO.getEmail(), connexionDTO.getPassword());
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new UserDTO(user)).build();
    }
}
