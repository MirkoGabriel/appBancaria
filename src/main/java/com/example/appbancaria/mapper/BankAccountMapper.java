package com.example.appbancaria.mapper;

import com.example.appbancaria.dto.BankAccountDTO;
import com.example.appbancaria.dto.BankAccountRequestDTO;
import com.example.appbancaria.models.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    BankAccount toEntity(BankAccountRequestDTO requestDto);

    BankAccountDTO toDto(BankAccount bankAccount);

    List<BankAccountDTO> toDtoList(List<BankAccount> bankAccountEntities);
}

