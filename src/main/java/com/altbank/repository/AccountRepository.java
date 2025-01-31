package com.altbank.repository;

import com.altbank.models.entity.Account;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {

    public Optional<Account> findByCardNumber(String cardNumber) {
        return find("SELECT a FROM Account a " +
                "JOIN FETCH a.physicalCards pc " +
                "JOIN FETCH a.customer c " +
                "WHERE pc.cardNumber = ?1", cardNumber)
                .firstResultOptional();
    }

}
