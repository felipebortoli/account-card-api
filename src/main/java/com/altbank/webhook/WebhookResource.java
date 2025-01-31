package com.altbank.webhook;


import io.quarkus.security.Authenticated;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/webhooks")
@Authenticated
public class WebhookResource {

    private final WebhookService webhookService;

    public WebhookResource(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @POST
    @Path("/delivery")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleDeliveryWebhook(DeliveryWebhookPayload payload, @HeaderParam("API-KEY") String apiKey) {
        if (!webhookService.isValidApiKey(apiKey)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        webhookService.processDelivery(payload);

        return Response.ok().build();
    }

    @POST
    @Path("/cvv-change")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleCvvChangeWebhook(CvvChangeWebhookPayload payload, @HeaderParam("API-KEY") String apiKey) {
        if (!webhookService.isValidApiKey(apiKey)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        webhookService.processCvvChange(payload);

        return Response.ok().build();
    }

}
