package com.bank.app.application.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MovementDTO {
    private Long id;
    private LocalDateTime date;
    private String type;
    private BigDecimal value;
    private BigDecimal balance;
    private String accountNumber;
}
