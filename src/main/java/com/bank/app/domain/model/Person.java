package com.bank.app.domain.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
    public abstract class Person{
        private String name;
        private String gender;
        private String identification;
        private String address;
        private String phone;
    }

