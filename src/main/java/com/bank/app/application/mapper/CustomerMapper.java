package com.bank.app.application.mapper;

import com.bank.app.domain.model.Customer;
import com.bank.app.infrastructure.persistence.entity.CustomerEntity;

public class CustomerMapper {

    public static Customer toDomain(CustomerEntity entity) {
        if (entity == null) return null;
        return Customer.builder()
                .id(entity.getId())
                .personId(entity.getPersonId())
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
                .personId(domain.getPersonId())
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
}
