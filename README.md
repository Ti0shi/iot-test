# IoT
## Database

The configuration of the database is available in the [database](database) folder. You can also use the online documentation SOON !

## Start the project

### Frontend

```bash
cd frontend
npm install
npm run dev
```

### Backend

```bash
cd backend
```

```bash
./mvnw quarkus:run OU execute the backend from IntelliJ
```


## Documentation

### Backend

The documentation of the backend is available at the following address, the swagger is avaiable ONLY when the backend is up: [http://localhost:8080/q/swagger-ui/](http://localhost:8080/q/swagger-ui/)

> ℹ There is also the YAML or JSON version of the documentation at the following address: [http://localhost:8080/q/openapi](http://localhost:8080/q/openapi)

Annotations needed for the documentation are detailed here: [https://download.eclipse.org/microprofile/microprofile-open-api-1.1.1/apidocs/overview-summary.html](https://download.eclipse.org/microprofile/microprofile-open-api-1.1.1/apidocs/overview-summary.html)

In short, here are the most common annotations:

```java
package com.huitiemev.iot.controller;

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
    public Response createUser(@Valid UserDTO userInfosDTO) {
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
        userService.createUser(user);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    public UserDTO getUserById(@PathParam("id") String id) {
        return new UserDTO(userService.getUserById(id));
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
}

```

