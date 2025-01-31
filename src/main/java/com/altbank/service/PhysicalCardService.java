package com.altbank.service;


import com.altbank.models.entity.Account;
import com.altbank.models.entity.Address;
import com.altbank.models.entity.Customer;
import com.altbank.models.entity.PhysicalCard;
import com.altbank.models.enums.CardStatus;
import com.altbank.models.request.ReissuePhysicalCardRequest;
import com.altbank.repository.PhysicalCardRepository;
import com.altbank.utils.CardNumberUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;

@ApplicationScoped
public class PhysicalCardService {

    private final PhysicalCardRepository physicalCardRepository;
    private final AccountService accountService;
    private final CustomerService customerService;

    public PhysicalCardService(PhysicalCardRepository physicalCardRepository, AccountService accountService, CustomerService customerService) {
        this.physicalCardRepository = physicalCardRepository;
        this.accountService = accountService;
        this.customerService = customerService;
    }

    public PhysicalCard createPhysicalCard(Account account){
        PhysicalCard newCard = PhysicalCard.builder()
                .cardNumber(CardNumberUtil.generateCardNumber())
                .status(CardStatus.PENDING)
                .expirationDate(LocalDate.now().plusYears(4))
                .trackingId(CardNumberUtil.generateTrackingId())
                .account(account)
                .build();
        physicalCardRepository.persist(newCard);
        return newCard;
    }

    @Transactional
    public PhysicalCard reissuePhysicalCard(ReissuePhysicalCardRequest request) {
        Account account = accountService.findByCardNumber(request.getCardNumber());
        PhysicalCard oldCard = physicalCardRepository.find("cardNumber", request.getCardNumber()).firstResult();
        if (oldCard != null) {
            oldCard.setStatus(CardStatus.REPLACED);
            physicalCardRepository.persist(oldCard);
        }
        if (request.getAddress() != null) {
            Customer customer = account.getCustomer();
            Address newAddress = Address.builder()
                    .zipCode(request.getAddress().getZipCode())
                    .street(request.getAddress().getStreet())
                    .number(request.getAddress().getNumber())
                    .state(request.getAddress().getState())
                    .city(request.getAddress().getCity())
                    .customer(customer)
                    .build();

            customer.setAddress(newAddress);
            customerService.createCustomer(customer);
        }
        PhysicalCard newCard = PhysicalCard.builder()
                .cardNumber(CardNumberUtil.generateCardNumber())
                .status(CardStatus.PENDING)
                .account(account)
                .trackingId(CardNumberUtil.generateTrackingId())
                .cvv(CardNumberUtil.generateRandomThreeDigitNumber())
                .expirationDate(LocalDate.now().plusYears(4))
                .build();
        physicalCardRepository.persist(newCard);
        return newCard;
    }

    public PhysicalCard findById(Long cardId) {
        return physicalCardRepository.findById(cardId);
    }

    public PhysicalCard find(String query, String trackingId) {
        return physicalCardRepository.find(query,trackingId).firstResult();
    }

    public void createPhysicalCard(PhysicalCard physicalCard) {
        physicalCardRepository.persist(physicalCard);
    }

}
