package com.bank.app.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementDTO {
    private Long id;
    private LocalDateTime date;
    @NotBlank(message = "El tipo de movimiento es obligatorio")
    private String type;
    @NotNull(message = "El valor es obligatorio")
    @Positive(message = "El valor debe ser mayor a cero")
    private BigDecimal value;
    private BigDecimal balance;
    @NotBlank(message = "El número de cuenta es obligatorio")
    private String accountNumber;
}
