package com.example.appbancaria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankAccountRequestDTO {
    @NotBlank
    private String accountNumber;
    @NotBlank
    private String ownerName;
    @NotNull
    private Double balance;
    @NotBlank
    private String status;
}
