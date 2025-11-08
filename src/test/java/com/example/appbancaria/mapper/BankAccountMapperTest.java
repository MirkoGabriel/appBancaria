package com.example.appbancaria.mapper;

import com.example.appbancaria.dto.BankAccountDTO;
import com.example.appbancaria.dto.BankAccountRequestDTO;
import com.example.appbancaria.models.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountMapperTest {

    private BankAccountMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new BankAccountMapperImpl();
    }

    @Test
    void toEntity_ShouldMapRequestDtoToEntity() {
        BankAccountRequestDTO requestDTO = BankAccountRequestDTO.builder()
                .accountNumber("123456")
                .ownerName("Juan Pérez")
                .balance(1000.0)
                .status("ACTIVE")
                .build();

        BankAccount entity = mapper.toEntity(requestDTO);

        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals("123456", entity.getAccountNumber());
        assertEquals("Juan Pérez", entity.getOwnerName());
        assertEquals(1000.0, entity.getBalance());
        assertEquals("ACTIVE", entity.getStatus());
        assertNotNull(entity.getCreatedAt());
    }

    @Test
    void toDto_ShouldMapEntityToDto() {
        BankAccount entity = new BankAccount();
        entity.setId(1L);
        entity.setAccountNumber("123456");
        entity.setOwnerName("Juan Pérez");
        entity.setBalance(1000.0);
        entity.setStatus("ACTIVE");
        entity.setCreatedAt(Instant.now());

        BankAccountDTO dto = mapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("123456", dto.getAccountNumber());
        assertEquals("Juan Pérez", dto.getOwnerName());
        assertEquals(1000.0, dto.getBalance());
        assertEquals("ACTIVE", dto.getStatus());
        assertNotNull(dto.getCreatedAt());
    }

    @Test
    void toDtoList_ShouldMapListOfEntities() {
        BankAccount entity1 = new BankAccount();
        entity1.setId(1L);
        entity1.setAccountNumber("123456");
        entity1.setOwnerName("Juan Pérez");
        entity1.setBalance(1000.0);
        entity1.setStatus("ACTIVE");

        BankAccount entity2 = new BankAccount();
        entity2.setId(2L);
        entity2.setAccountNumber("789012");
        entity2.setOwnerName("María García");
        entity2.setBalance(2000.0);
        entity2.setStatus("ACTIVE");

        List<BankAccount> entities = Arrays.asList(entity1, entity2);

        List<BankAccountDTO> dtos = mapper.toDtoList(entities);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(1L, dtos.get(0).getId());
        assertEquals(2L, dtos.get(1).getId());
    }
}

