package com.altbank.controller;

import com.altbank.models.request.ReissuePhysicalCardRequest;
import com.altbank.service.PhysicalCardService;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import jakarta.ws.rs.core.Response;


@Path("v1/api/card")
public class PhysicalCardController {

    private final PhysicalCardService service;

    public PhysicalCardController(PhysicalCardService service) {
        this.service = service;
    }

    @PUT
    @Path("/reissue")
    public Response reissueCard(ReissuePhysicalCardRequest request) {
        service.reissuePhysicalCard(request);
        return Response.ok("Card reissue request submitted successfully.").build();
    }
}
