package com.example.appbancaria.service;

import com.example.appbancaria.dto.BankAccountDTO;
import com.example.appbancaria.dto.BankAccountRequestDTO;
import com.example.appbancaria.exception.DuplicateResourceException;
import com.example.appbancaria.exception.ResourceNotFoundException;
import com.example.appbancaria.mapper.BankAccountMapper;
import com.example.appbancaria.models.BankAccount;
import com.example.appbancaria.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {
    private final BankAccountRepository repo;
    private final BankAccountMapper mapper;

    public BankAccountService(BankAccountRepository repo, BankAccountMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public BankAccountDTO create(BankAccountRequestDTO requestDto) {
        BankAccount acc = mapper.toEntity(requestDto);
        
        if (repo.existsByAccountNumber(acc.getAccountNumber())) {
            throw new DuplicateResourceException("Account number already exists: " + acc.getAccountNumber());
        }
        
        BankAccount saved = repo.save(acc);
        return mapper.toDto(saved);
    }

    public BankAccountDTO getById(Long id) {
        BankAccount entity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));

        return mapper.toDto(entity);
    }

    public List<BankAccountDTO> getAll() {
        List<BankAccount> entities = repo.findAll();
        return mapper.toDtoList(entities);
    }

    public BankAccountDTO update(Long id, BankAccountRequestDTO requestDto) {
        BankAccount existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
        
        BankAccount update = mapper.toEntity(requestDto);
        
        if (!existing.getAccountNumber().equals(update.getAccountNumber())
                && repo.existsByAccountNumber(update.getAccountNumber())) {
            throw new DuplicateResourceException("Account number already exists: " + update.getAccountNumber());
        }
        
        existing.setOwnerName(update.getOwnerName());
        existing.setBalance(update.getBalance());
        existing.setAccountNumber(update.getAccountNumber());
        existing.setStatus(update.getStatus());
        
        BankAccount saved = repo.save(existing);
        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        BankAccount entity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
        repo.delete(entity);
    }

}
