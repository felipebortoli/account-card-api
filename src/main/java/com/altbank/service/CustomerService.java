package com.altbank.service;


import com.altbank.models.entity.*;
import com.altbank.models.request.AddressRequest;
import com.altbank.models.request.CustomerRequest;
import com.altbank.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountService accountService;
    private final PhysicalCardService physicalCardService;
    private final VirtualCardService virtualCardService;

    public CustomerService(CustomerRepository customerRepository, AccountService accountService, PhysicalCardService physicalCardService, VirtualCardService virtualCardService) {
        this.customerRepository = customerRepository;
        this.accountService = accountService;
        this.physicalCardService = physicalCardService;
        this.virtualCardService = virtualCardService;
    }

    @Transactional
    public Customer createCustomer(CustomerRequest request) {
        AddressRequest addressRequest = request.getAddress();
        Address address = Address.builder()
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .number(addressRequest.getNumber())
                .zipCode(addressRequest.getZipCode())
                .street(addressRequest.getStreet())
                .build();
        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .documentNumber(request.getDocumentNumber())
                .address(address)
                .build();
        customerRepository.persist(customer);
        Account account = accountService.createAccount(customer.getId());

        PhysicalCard physicalCard = physicalCardService.createPhysicalCard(account);

        VirtualCard virtualCard = virtualCardService.create(account);

        account.getPhysicalCards().add(physicalCard);
        account.getVirtualCards().add(virtualCard);

        accountService.createAccount(account);

        return customer;
    }

    public Customer findById(Long idCustomer) {
        Optional<Customer> customerOpt = customerRepository.findByIdOptional(idCustomer);
        if (customerOpt.isEmpty()) {
            throw new IllegalArgumentException("Customer not found");
        }
        return customerOpt.get();
    }

    public void createCustomer(Customer customer) {
        customerRepository.persist(customer);
    }
}
