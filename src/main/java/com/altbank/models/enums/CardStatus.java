package com.altbank.models.enums;

public enum CardStatus {
    PENDING,        // Card is requested but not yet issued or sent
    ISSUED,         // Card has been generated and is ready to be sent
    SENT,           // Card has been sent to the customer but not delivered yet
    DELIVERED,      // Card has been delivered to the customer
    VALIDATED,      // Card has been validated after delivery and the customer can request a virtual card
    BLOCKED,        // Card has been blocked (e.g., lost, stolen)
    CANCELED,       // Card has been canceled
    REPLACED        // Card has been replaced due to loss, theft, or other reasons
}
