package com.bank.app.application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
    private String number;
    private String type;
    private BigDecimal initialBalance;
    private Boolean status;
    private Long customerId;
}
