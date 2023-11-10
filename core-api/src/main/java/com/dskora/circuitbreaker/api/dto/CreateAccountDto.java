package com.dskora.circuitbreaker.api.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateAccountDto {
    private String firstname;
    private String surname;
    private BigDecimal initialBalance;
}
