package com.bank.app.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    @NotBlank(message = "El número de cuenta es obligatorio")
    private String number;
    @NotBlank(message = "El tipo de cuenta es obligatorio")
    private String type;
    @NotNull(message = "El saldo inicial es obligatorio")
    @PositiveOrZero(message = "El saldo no puede ser negativo")
    private BigDecimal balance;
    private Boolean status;
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long customerId;
}
