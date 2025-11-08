package com.example.appbancaria.service;

import com.example.appbancaria.dto.BankAccountDTO;
import com.example.appbancaria.dto.BankAccountRequestDTO;
import com.example.appbancaria.exception.DuplicateResourceException;
import com.example.appbancaria.exception.ResourceNotFoundException;
import com.example.appbancaria.mapper.BankAccountMapper;
import com.example.appbancaria.models.BankAccount;
import com.example.appbancaria.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

    @Mock
    private BankAccountRepository repository;

    @Mock
    private BankAccountMapper mapper;

    @InjectMocks
    private BankAccountService service;

    private BankAccount bankAccount;
    private BankAccountDTO bankAccountDTO;
    private BankAccountRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setAccountNumber("123456");
        bankAccount.setOwnerName("Juan Pérez");
        bankAccount.setBalance(1000.0);
        bankAccount.setStatus("ACTIVE");
        bankAccount.setCreatedAt(Instant.now());

        bankAccountDTO = BankAccountDTO.builder()
                .id(1L)
                .accountNumber("123456")
                .ownerName("Juan Pérez")
                .balance(1000.0)
                .status("ACTIVE")
                .createdAt(Instant.now())
                .build();

        requestDTO = BankAccountRequestDTO.builder()
                .accountNumber("123456")
                .ownerName("Juan Pérez")
                .balance(1000.0)
                .status("ACTIVE")
                .build();
    }

    @Test
    void create_ShouldReturnCreatedAccount() {
        when(mapper.toEntity(requestDTO)).thenReturn(bankAccount);
        when(repository.existsByAccountNumber("123456")).thenReturn(false);
        when(repository.save(bankAccount)).thenReturn(bankAccount);
        when(mapper.toDto(bankAccount)).thenReturn(bankAccountDTO);

        BankAccountDTO result = service.create(requestDTO);

        assertNotNull(result);
        assertEquals("123456", result.getAccountNumber());
        verify(repository, times(1)).save(bankAccount);
    }

    @Test
    void create_ShouldThrowException_WhenAccountNumberExists() {
        when(mapper.toEntity(requestDTO)).thenReturn(bankAccount);
        when(repository.existsByAccountNumber("123456")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> service.create(requestDTO));
        verify(repository, never()).save(any());
    }

    @Test
    void getById_ShouldReturnAccount() {
        when(repository.findById(1L)).thenReturn(Optional.of(bankAccount));
        when(mapper.toDto(bankAccount)).thenReturn(bankAccountDTO);

        BankAccountDTO result = service.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getById_ShouldThrowException_WhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(1L));
    }

    @Test
    void getAll_ShouldReturnAllAccounts() {
        List<BankAccount> accounts = Arrays.asList(bankAccount);
        when(repository.findAll()).thenReturn(accounts);
        when(mapper.toDtoList(accounts)).thenReturn(Arrays.asList(bankAccountDTO));

        List<BankAccountDTO> result = service.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void update_ShouldReturnUpdatedAccount() {
        when(repository.findById(1L)).thenReturn(Optional.of(bankAccount));
        when(mapper.toEntity(requestDTO)).thenReturn(bankAccount);
        when(repository.save(bankAccount)).thenReturn(bankAccount);
        when(mapper.toDto(bankAccount)).thenReturn(bankAccountDTO);

        BankAccountDTO result = service.update(1L, requestDTO);

        assertNotNull(result);
        verify(repository, times(1)).save(bankAccount);
    }

    @Test
    void delete_ShouldDeleteAccount() {
        when(repository.findById(1L)).thenReturn(Optional.of(bankAccount));
        doNothing().when(repository).delete(bankAccount);

        service.delete(1L);

        verify(repository, times(1)).delete(bankAccount);
    }

    @Test
    void delete_ShouldThrowException_WhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
    }
}

