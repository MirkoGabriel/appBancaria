package com.example.appbancaria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountClientResponse {
    private Long id;
    private String accountNumber;
    private String ownerName;
    private Double balance;
    private String status;
    private Instant createdAt;
}

