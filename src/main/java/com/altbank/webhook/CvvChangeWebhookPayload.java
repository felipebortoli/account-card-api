package com.altbank.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CvvChangeWebhookPayload {
    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("card_id")
    private String cardId;

    @JsonProperty("next_cvv")
    private int nextCvv;

    @JsonProperty("expiration_date")
    private LocalDateTime expirationDate;
}
