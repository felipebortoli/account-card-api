package com.altbank.models.entity;

import com.altbank.models.enums.CardStatus;
import com.altbank.models.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CardStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingId;

    private String deliveryReturnReason;

    @ManyToOne
    @JoinColumn(name = "physical_card_id", nullable = false)
    private PhysicalCard physicalCard;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    @Column(nullable = false)
    private LocalDateTime changeDate;

    private String deliveryAddress;
}
