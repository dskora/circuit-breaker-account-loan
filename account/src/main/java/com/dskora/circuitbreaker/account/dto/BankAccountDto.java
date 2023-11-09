package com.dskora.circuitbreaker.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {
    private UUID id;
    private String firstname;
    private String surname;
    private BigDecimal balance;
}
