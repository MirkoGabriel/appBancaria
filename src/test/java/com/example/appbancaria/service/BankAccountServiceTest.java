package com.example.appbancaria.service;

import com.example.appbancaria.dto.BankAccountRequestDTO;
import com.example.appbancaria.exception.DuplicateResourceException;
import com.example.appbancaria.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BankAccountServiceTest {
    @Autowired
    BankAccountService service;

    @Autowired
    BankAccountRepository repo;

    @BeforeEach
    void before() { repo.deleteAll(); }

    @Test
    void create_duplicate_throws() {
        BankAccountRequestDTO a = BankAccountRequestDTO.builder()
                .accountNumber("A1")
                .ownerName("X")
                .balance(10.0)
                .build();
        service.create(a);

        BankAccountRequestDTO b = BankAccountRequestDTO.builder()
                .accountNumber("A1")
                .ownerName("Y")
                .balance(5.0)
                .build();

        assertThrows(DuplicateResourceException.class, () -> service.create(b));
    }
}
