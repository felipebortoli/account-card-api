package com.altbank.service;

import com.altbank.models.entity.Account;
import com.altbank.models.entity.Customer;
import com.altbank.repository.AccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    public AccountService(AccountRepository accountRepository, CustomerService customerService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
    }

    @Transactional
    public Account createAccount(Long idCustomer) {
        Customer customer = customerService.findById(idCustomer);
        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .customer(customer)
                .physicalCards(new ArrayList<>())
                .virtualCards(new ArrayList<>())
                .active(true)
                .build();
        accountRepository.persist(account);
        return account;
    }

    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }

    public void createAccount(Account account) {
        accountRepository.persist(account);
    }

    @Transactional
    public void cancelAccount(String accountNumber) {
        Account account = accountRepository.find("accountNumber", accountNumber).firstResultOptional() .orElseThrow(() -> new RuntimeException("Account not found for account number: " + accountNumber));
        account.setActive(false);
        accountRepository.persist(account);
    }

    public Account findByCardNumber(String cardNumber) {
        return accountRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Account not found for card number: " + cardNumber));
    }


}
