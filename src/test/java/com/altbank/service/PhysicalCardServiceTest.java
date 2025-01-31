package com.altbank.service;

import com.altbank.models.entity.Account;
import com.altbank.models.entity.Customer;
import com.altbank.models.entity.PhysicalCard;
import com.altbank.models.enums.CardStatus;
import com.altbank.models.request.AddressRequest;
import com.altbank.models.request.ReissuePhysicalCardRequest;
import com.altbank.repository.PhysicalCardRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PhysicalCardServiceTest {

    @Mock
    private PhysicalCardRepository physicalCardRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private PhysicalCardService physicalCardService;

    private Account account;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        account = new Account();
        account.setId(1L);
        account.setCustomer(customer);

        PhysicalCard physicalCard = PhysicalCard.builder()
                .cardNumber("1234567890")
                .status(CardStatus.PENDING)
                .expirationDate(LocalDate.now().plusYears(4))
                .account(account)
                .build();

        when(physicalCardRepository.find("cardNumber", "1234567890")).thenReturn(mock(PanacheQuery.class));
    }

    @Test
    public void testCreatePhysicalCard() {
        PhysicalCard newCard = physicalCardService.createPhysicalCard(account);

        assertNotNull(newCard);
        assertEquals(CardStatus.PENDING, newCard.getStatus());
        assertEquals(account, newCard.getAccount());

        verify(physicalCardRepository, times(1)).persist(any(PhysicalCard.class));
    }

    @Test
    public void testFindById() {
        Long cardId = 1L;

        PhysicalCard physicalCard = new PhysicalCard();
        physicalCard.setId(cardId);

        when(physicalCardRepository.findById(cardId)).thenReturn(physicalCard);

        PhysicalCard foundCard = physicalCardService.findById(cardId);

        assertNotNull(foundCard);
        assertEquals(cardId, foundCard.getId());
    }


}
