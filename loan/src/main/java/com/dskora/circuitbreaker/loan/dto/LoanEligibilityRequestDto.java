package com.dskora.circuitbreaker.loan.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class LoanEligibilityRequestDto {
    private BigDecimal balance;

    public LoanEligibilityRequestDto(BigDecimal balance)
    {
        this.balance = balance;
    }
}
