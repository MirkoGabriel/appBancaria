package com.example.appbancaria.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "bank_accounts", uniqueConstraints = {
        @UniqueConstraint(columnNames = "accountNumber")
})
@Data
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private Double balance = 0.0;

    @Column(nullable = false)
    private String status = "ACTIVE";

    private Instant createdAt = Instant.now();
}
