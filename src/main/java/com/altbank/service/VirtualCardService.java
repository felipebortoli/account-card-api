package com.altbank.service;


import com.altbank.models.entity.Account;
import com.altbank.models.entity.VirtualCard;
import com.altbank.repository.VirtualCardRepository;
import com.altbank.utils.CardNumberUtil;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApplicationScoped
public class VirtualCardService {

   private final VirtualCardRepository virtualCardRepository;


    public VirtualCardService(VirtualCardRepository virtualCardRepository) {
        this.virtualCardRepository = virtualCardRepository;
    }

    public VirtualCard create(Account account) {
        VirtualCard virtualCard = VirtualCard
                .builder()
                .account(account)
                .cardNumber(CardNumberUtil.generateCardNumber())
                .cvv(CardNumberUtil.generateRandomThreeDigitNumber())
                .expirationDateCvv(LocalDateTime.now().plusDays(1))
                .expirationDate(LocalDate.now().plusYears(4))
                .build();
        virtualCardRepository.persist(virtualCard);
        return virtualCard;
    }

    public VirtualCard find(String query, String cardId) {
        return virtualCardRepository.find(query,cardId).firstResult();
    }

    public VirtualCard update(VirtualCard virtualCard) {
        virtualCardRepository.persist(virtualCard);
        return virtualCard;
    }
}
