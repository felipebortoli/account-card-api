package com.altbank.repository;

import com.altbank.models.entity.CardStatusHistory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CardStatusHistoryRepository  implements PanacheRepository<CardStatusHistory> {
}
