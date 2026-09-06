package com.bank.app.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;

    @NotBlank(message = "El número de cuenta es obligatorio")
    private String number;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    private String type;

    @NotNull(message = "El saldo es obligatorio")
    @PositiveOrZero(message = "El saldo debe ser mayor o igual a cero")
    private BigDecimal balance;

    private Boolean status;

    private Customer customer;
}