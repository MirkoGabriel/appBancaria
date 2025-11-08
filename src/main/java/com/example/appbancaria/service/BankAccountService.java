package com.example.appbancaria.service;

import com.example.appbancaria.exception.DuplicateResourceException;
import com.example.appbancaria.exception.ResourceNotFoundException;
import com.example.appbancaria.models.BankAccount;
import com.example.appbancaria.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {
    private final BankAccountRepository repo;

    public BankAccountService(BankAccountRepository repo) {
        this.repo = repo;
    }

    public BankAccount create(BankAccount acc) {
        if (repo.existsByAccountNumber(acc.getAccountNumber())) {
            throw new DuplicateResourceException("Account number already exists: " + acc.getAccountNumber());
        }
        return repo.save(acc);
    }

    public BankAccount getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
    }

    public List<BankAccount> getAll() {
        return repo.findAll();
    }

    public BankAccount update(Long id, BankAccount update) {
        BankAccount existing = getById(id);
        if (!existing.getAccountNumber().equals(update.getAccountNumber())
                && repo.existsByAccountNumber(update.getAccountNumber())) {
            throw new DuplicateResourceException("Account number already exists: " + update.getAccountNumber());
        }
        existing.setOwnerName(update.getOwnerName());
        existing.setBalance(update.getBalance());
        existing.setAccountNumber(update.getAccountNumber());
        existing.setStatus(update.getStatus());
        return repo.save(existing);
    }

    public void delete(Long id) {
        BankAccount e = getById(id);
        repo.delete(e);
    }

}
