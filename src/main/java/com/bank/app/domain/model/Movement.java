package com.bank.app.domain.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movement {

    private Long id;
    private LocalDateTime date;
    private String type;

    @NotNull(message = "El valor del movimiento es obligatorio")
    private BigDecimal value;

    @NotNull(message = "El saldo es obligatorio")
    private BigDecimal balance;

    @NotNull(message = "La cuenta asociada es obligatoria")
    private Account account;
}