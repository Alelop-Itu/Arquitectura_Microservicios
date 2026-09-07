package com.bank.app.infrastructure.mapper;

import com.bank.app.application.dto.MovementDTO;
import com.bank.app.domain.model.Movement;
import com.bank.app.infrastructure.persistence.entity.MovementEntity;

public class MovementMapper {

    public static Movement toDomain(MovementEntity entity) {
        if (entity == null) return null;
        return Movement.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .type(entity.getType())
                .value(entity.getValue())
                .balance(entity.getBalance())
                .account(AccountMapper.toDomain(entity.getAccount()))
                .build();
    }

    public static MovementEntity toEntity(Movement domain) {
        if (domain == null) return null;
        return MovementEntity.builder()
                .id(domain.getId())
                .date(domain.getDate())
                .type(domain.getType())
                .value(domain.getValue())
                .balance(domain.getBalance())
                .account(AccountMapper.toEntity(domain.getAccount()))
                .build();
    }

    public static MovementDTO toDto(Movement domain) {
        if (domain == null) return null;
        return new MovementDTO(
                domain.getId(),
                domain.getDate(),
                domain.getType(),
                domain.getValue(),
                domain.getBalance(),
                domain.getAccount() != null ? domain.getAccount().getNumber() : null
        );
    }
}