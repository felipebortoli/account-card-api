package com.altbank.webhook;

import com.altbank.models.enums.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DeliveryWebhookPayload {
    @JsonProperty("tracking_id")
    private String trackingId;

    @JsonProperty("delivery_status")
    private DeliveryStatus deliveryStatus;

    @JsonProperty("delivery_date")
    private LocalDateTime deliveryDate;

    @JsonProperty("delivery_return_reason")
    private String deliveryReturnReason;

    @JsonProperty("delivery_address")
    private String deliveryAddress;
}
