package com.altbank.repository;

import com.altbank.models.entity.PhysicalCard;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PhysicalCardRepository implements PanacheRepository<PhysicalCard> {
}
