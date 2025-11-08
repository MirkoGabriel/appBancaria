package com.example.appbancaria.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankAccountDto {
    private Long id;
    private String accountNumber;
    private String ownerName;
    private Double balance;
    private String status;
    private String createdAt;
}
