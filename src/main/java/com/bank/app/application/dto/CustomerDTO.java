package com.bank.app.application.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String gender;
    private String identification;
    private String adress;
    private String phone;
    private String password;
    private Boolean status;
}
