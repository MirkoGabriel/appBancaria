package com.example.appbancaria.controller;

import com.example.appbancaria.dto.BankAccountDTO;
import com.example.appbancaria.dto.BankAccountRequestDTO;
import com.example.appbancaria.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountControllerTest {

    @Mock
    private BankAccountService service;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BankAccountController controller;

    private BankAccountDTO bankAccountDTO;
    private BankAccountRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
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
    void create_ShouldReturnCreatedStatus() {
        when(service.create(any(BankAccountRequestDTO.class))).thenReturn(bankAccountDTO);

        ResponseEntity<BankAccountDTO> response = controller.create(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("123456", response.getBody().getAccountNumber());
        verify(service, times(1)).create(requestDTO);
    }

    @Test
    void get_ShouldReturnBankAccount() {
        when(service.getById(1L)).thenReturn(bankAccountDTO);

        BankAccountDTO result = controller.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("123456", result.getAccountNumber());
        verify(service, times(1)).getById(1L);
    }

    @Test
    void list_ShouldReturnAllAccounts() {
        List<BankAccountDTO> accounts = Arrays.asList(bankAccountDTO);
        when(service.getAll()).thenReturn(accounts);

        List<BankAccountDTO> result = controller.list();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(service, times(1)).getAll();
    }

    @Test
    void update_ShouldReturnUpdatedAccount() {
        when(service.update(eq(1L), any(BankAccountRequestDTO.class))).thenReturn(bankAccountDTO);

        BankAccountDTO result = controller.update(1L, requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(service, times(1)).update(1L, requestDTO);
    }

    @Test
    void delete_ShouldReturnNoContent() {
        doNothing().when(service).delete(1L);

        ResponseEntity<?> response = controller.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(1L);
    }
}

