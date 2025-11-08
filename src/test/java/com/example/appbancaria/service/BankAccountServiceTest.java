package com.example.appbancaria.service;

import com.example.appbancaria.exception.DuplicateResourceException;
import com.example.appbancaria.models.BankAccount;
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
        BankAccount a = new BankAccount();
        a.setAccountNumber("A1");
        a.setOwnerName("X");
        a.setBalance(10.0);
        service.create(a);

        BankAccount b = new BankAccount();
        b.setAccountNumber("A1");
        b.setOwnerName("Y");
        b.setBalance(5.0);

        assertThrows(DuplicateResourceException.class, () -> service.create(b));
    }
}
