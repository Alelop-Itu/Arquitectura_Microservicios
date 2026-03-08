package com.bank.app.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor


public abstract class Person {
    @Column(nullable = false)
    private String name;
    private String gender;

    @Column(unique = true, nullable = false)
    private String identification;
    private String address;
    private String phone;
}

