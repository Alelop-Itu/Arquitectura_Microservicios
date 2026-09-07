package com.bank.app.infrastructure.mapper;

import com.bank.app.application.dto.AccountDTO;
import com.bank.app.domain.model.Account;
import com.bank.app.infrastructure.persistence.entity.AccountEntity;

public class AccountMapper {

    public static Account toDomain(AccountEntity entity) {
        if (entity == null) return null;
        return Account.builder()
                .id(entity.getId())
                .number(entity.getNumber())
                .type(entity.getType())
                .balance(entity.getBalance())
                .status(entity.getStatus())
                .customer(CustomerMapper.toDomain(entity.getCustomer()))
                .build();
    }

    public static AccountEntity toEntity(Account domain) {
        if (domain == null) return null;
        return AccountEntity.builder()
                .id(domain.getId())
                .number(domain.getNumber())
                .type(domain.getType())
                .balance(domain.getBalance())
                .status(domain.getStatus())
                .customer(CustomerMapper.toEntity(domain.getCustomer()))
                .build();
    }

    public static AccountDTO toDto(Account domain) {
        if (domain == null) return null;
        return new AccountDTO(
                domain.getNumber(),
                domain.getType(),
                domain.getBalance(),
                domain.getStatus(),
                domain.getCustomer() != null ? domain.getCustomer().getId() : null
        );
    }
}