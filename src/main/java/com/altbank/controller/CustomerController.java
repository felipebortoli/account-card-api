package com.altbank.controller;


import com.altbank.models.request.CustomerRequest;
import com.altbank.service.CustomerService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("v1/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(CustomerRequest request) {
        customerService.createCustomer(request);
        return  Response.status(Response.Status.CREATED).build();
    }

}
