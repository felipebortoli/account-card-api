package com.altbank.models.entity;

import jakarta.persistence.*;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String documentNumber;

    private String name;
    private String email;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @ManyToOne(cascade = CascadeType.PERSIST)
    public Account account;
}
