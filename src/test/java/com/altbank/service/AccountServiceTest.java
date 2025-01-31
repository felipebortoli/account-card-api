package com.altbank.service;



import com.altbank.models.entity.Account;
import com.altbank.models.entity.Customer;
import com.altbank.repository.AccountRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private Customer customer;

    @Mock
    private Account account;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testCreateAccount() {
        Long customerId = 1L;

        when(customerService.findById(customerId)).thenReturn(customer);

        doNothing().when(accountRepository).persist(any(Account.class));

        Account newAccount = accountService.createAccount(customerId);

        assertNotNull(newAccount);
        assertEquals(customer, newAccount.getCustomer());
        verify(accountRepository, times(1)).persist(any(Account.class));
    }

    @Test
    public void testCancelAccount() {
        String accountNumber = "ACC12345";

        when(accountRepository.find("accountNumber", accountNumber)).thenReturn(mock(PanacheQuery.class));
        when(accountRepository.find("accountNumber", accountNumber).firstResultOptional()).thenReturn(Optional.of(account));

        doNothing().when(accountRepository).persist(account);

        accountService.cancelAccount(accountNumber);

        verify(accountRepository, times(1)).persist(account);

        assertFalse(account.isActive());
    }

    @Test
    public void testFindByCardNumber() {
        String cardNumber = "CARD12345";

        when(accountRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(account));

        Account foundAccount = accountService.findByCardNumber(cardNumber);

        assertNotNull(foundAccount);
        verify(accountRepository, times(1)).findByCardNumber(cardNumber);
    }

    @Test
    public void testFindByCardNumber_NotFound() {
        String cardNumber = "CARD12345";

        when(accountRepository.findByCardNumber(cardNumber)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountService.findByCardNumber(cardNumber));

        assertEquals("Account not found for card number: " + cardNumber, exception.getMessage());
    }

}