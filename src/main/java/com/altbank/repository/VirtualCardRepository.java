package com.altbank.repository;

import com.altbank.models.entity.VirtualCard;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VirtualCardRepository implements PanacheRepository<VirtualCard> {
}
