package com.altbank.webhook;

import com.altbank.models.entity.*;
import com.altbank.models.enums.CardStatus;
import com.altbank.models.enums.DeliveryStatus;
import com.altbank.repository.CardStatusHistoryRepository;
import com.altbank.service.AccountService;
import com.altbank.service.PhysicalCardService;
import com.altbank.service.VirtualCardService;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;

@ApplicationScoped
public class WebhookService {
    private final PhysicalCardService physicalCardService;
    private final VirtualCardService virtualCardService;
    private final CardStatusHistoryRepository cardStatusHistoryRepository;

    public WebhookService(PhysicalCardService physicalCardService, VirtualCardService virtualCardService, CardStatusHistoryRepository cardStatusHistoryRepository) {
        this.physicalCardService = physicalCardService;
        this.virtualCardService = virtualCardService;
        this.cardStatusHistoryRepository = cardStatusHistoryRepository;
    }

    public void processDelivery(DeliveryWebhookPayload payload) {
        PhysicalCard card = physicalCardService.find("cardNumber", payload.getTrackingId());
        if(card == null) {
            throw new IllegalArgumentException("Card not found");
        }
        CardStatusHistory statusHistory = CardStatusHistory.builder()
                .physicalCard(card)
                .status(payload.getDeliveryStatus())
                .changeDate(LocalDateTime.now())
                .deliveryAddress(payload.getDeliveryAddress())
                .trackingId(payload.getTrackingId())
                .deliveryReturnReason(payload.getDeliveryReturnReason())
                .build();

        cardStatusHistoryRepository.persist(statusHistory);
        if (DeliveryStatus.DELIVERED.equals(payload.getDeliveryStatus())) {
            card.setStatus(CardStatus.DELIVERED);
            physicalCardService.createPhysicalCard(card);
        } else if (DeliveryStatus.DELIVERY_FAILED.equals(payload.getDeliveryStatus())) {
            card.setStatus(CardStatus.BLOCKED);
            physicalCardService.createPhysicalCard(card);
        }
    }

    public boolean isValidApiKey(String apiKey) {
        return "valid-api-key-altibank".equals(apiKey);
    }

    public void processCvvChange(CvvChangeWebhookPayload payload) {
        VirtualCard card = virtualCardService.find("cardNumber", payload.getCardId());
        if(card == null) {
            throw new IllegalArgumentException("Card not found");
        }
        card.setCvv(payload.getNextCvv());
        card.setExpirationDateCvv(payload.getExpirationDate());
        virtualCardService.update(card);
    }
}
