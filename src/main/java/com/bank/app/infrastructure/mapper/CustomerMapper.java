package com.bank.app.infrastructure.mapper;

import com.bank.app.application.dto.CustomerDTO;
import com.bank.app.domain.model.Customer;
import com.bank.app.infrastructure.persistence.entity.CustomerEntity;

public class CustomerMapper {

    public static Customer toDomain(CustomerEntity entity) {
        if (entity == null) return null;
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .gender(entity.getGender())
                .age(entity.getAge())
                .identification(entity.getIdentification())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .password(entity.getPassword())
                .status(entity.getStatus())
                .build();
    }

    public static CustomerEntity toEntity(Customer domain) {
        if (domain == null) return null;
        return CustomerEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .gender(domain.getGender())
                .age(domain.getAge())
                .identification(domain.getIdentification())
                .address(domain.getAddress())
                .phone(domain.getPhone())
                .password(domain.getPassword())
                .status(domain.getStatus())
                .build();
    }

    public static Customer toDomain(CustomerDTO dto) {
        if (dto == null) return null;
        return Customer.builder()
                .id(dto.getId())
                .name(dto.getName())
                .gender(dto.getGender())
                .identification(dto.getIdentification())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .password(dto.getPassword())
                .status(dto.getStatus())
                .build();
    }

    public static CustomerDTO toDto(Customer domain) {
        if (domain == null) return null;
        return new CustomerDTO(
                domain.getId(),
                domain.getName(),
                domain.getGender(),
                domain.getIdentification(),
                domain.getAddress(),
                domain.getPhone(),
                null,
                domain.getStatus()
        );
    }
}