package com.example.appbancaria.controller;

import com.example.appbancaria.models.BankAccount;
import com.example.appbancaria.service.BankAccountService;
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
    public ResponseEntity<BankAccount> create(
            @RequestBody BankAccount acc) {
        BankAccount created = service.create(acc);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public BankAccount get(
            @PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<BankAccount> list() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public BankAccount update(
            @PathVariable Long id,
            @RequestBody BankAccount acc) {
        return service.update(id, acc);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/self-call/list")
    public ResponseEntity<List<BankAccount>> selfCall() {
        String url = "http://localhost:8080/api/accounts";
        ResponseEntity<List<BankAccount>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BankAccount>>() {}
        );
        return ResponseEntity.ok(response.getBody());
    }
}
