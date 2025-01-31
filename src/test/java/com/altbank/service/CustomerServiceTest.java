package com.altbank.service;

import com.altbank.models.entity.Account;
import com.altbank.models.entity.Customer;
import com.altbank.models.entity.PhysicalCard;
import com.altbank.models.entity.VirtualCard;
import com.altbank.models.request.AddressRequest;
import com.altbank.models.request.CustomerRequest;
import com.altbank.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private PhysicalCardService physicalCardService;

    @Mock
    private VirtualCardService virtualCardService;

    @Mock
    private Customer customer;

    @Mock
    private Account account;

    @Mock
    private PhysicalCard physicalCard;

    @Mock
    private VirtualCard virtualCard;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testCreateCustomer() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("John Doe");
        customerRequest.setEmail("john@example.com");
        customerRequest.setDocumentNumber("123456789");

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setStreet("123 Main St");
        addressRequest.setCity("City");
        addressRequest.setState("State");
        addressRequest.setZipCode("12345");
        addressRequest.setCountry("Country");
        addressRequest.setComplement("Apt 1");
        addressRequest.setNumber("100");

        customerRequest.setAddress(addressRequest);

        Account account = new Account();
        account.setPhysicalCards(new ArrayList<>());
        account.setVirtualCards(new ArrayList<>());
        account.setActive(true);


        Customer customer = new Customer();
        customer.setId(1L);

        doAnswer(invocation -> {
            Customer arg = invocation.getArgument(0);
            arg.setId(1L);
            return null;
        }).when(customerRepository).persist(any(Customer.class));

        doNothing().when(customerRepository).persist(customer);
        when(accountService.createAccount(anyLong())).thenReturn(account);
        when(physicalCardService.createPhysicalCard(any(Account.class))).thenReturn(new PhysicalCard());
        when(virtualCardService.create(any(Account.class))).thenReturn(new VirtualCard());


        Customer createdCustomer = customerService.createCustomer(customerRequest);

        assertNotNull(createdCustomer);
        assertNotNull(createdCustomer.getAddress());
        assertTrue(createdCustomer.getName().equals("John Doe"));
        assertTrue(createdCustomer.getEmail().equals("john@example.com"));

        verify(accountService, times(1)).createAccount(anyLong());
        verify(physicalCardService, times(1)).createPhysicalCard(any(Account.class));
        verify(virtualCardService, times(1)).create(any(Account.class));
    }


    @Test
    public void testFindById_CustomerNotFound() {
        Long customerId = 1L;

        when(customerRepository.findByIdOptional(customerId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> customerService.findById(customerId));
    }

 }