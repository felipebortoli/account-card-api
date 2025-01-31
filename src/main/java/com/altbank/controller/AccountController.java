package com.altbank.controller;


import com.altbank.service.AccountService;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("v1/api/account")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }


    @PUT
    @Path("/cancel/{accountNumber}")
    public Response cancelAccount(@PathParam("accountNumber") String AccountNumber) {
        service.cancelAccount(AccountNumber);
        return Response.ok("Account canceled successfully.").build();
    }
}
