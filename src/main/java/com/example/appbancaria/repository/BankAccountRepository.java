package com.example.appbancaria.repository;

import com.example.appbancaria.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    boolean existsByAccountNumber(String accountNumber);
}