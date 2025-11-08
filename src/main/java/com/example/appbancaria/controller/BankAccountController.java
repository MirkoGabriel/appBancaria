package com.example.appbancaria.controller;

import com.example.appbancaria.dto.BankAccountDTO;
import com.example.appbancaria.dto.BankAccountRequestDTO;
import com.example.appbancaria.service.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {
    private final BankAccountService service;
    private final RestTemplate restTemplate;

    public BankAccountController(BankAccountService service, RestTemplate restTemplate) {
        this.service = service;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<BankAccountDTO> create(
            @Valid @RequestBody BankAccountRequestDTO requestDto) {
        BankAccountDTO created = service.create(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public BankAccountDTO get(
            @PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<BankAccountDTO> list() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public BankAccountDTO update(
            @PathVariable Long id,
            @Valid @RequestBody BankAccountRequestDTO requestDto) {
        return service.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/self-call/list")
    public ResponseEntity<List<BankAccountDTO>> selfCall() {
        String url = "http://localhost:8080/api/accounts";
        ResponseEntity<List<BankAccountDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BankAccountDTO>>() {}
        );
        return ResponseEntity.ok(response.getBody());
    }
}
