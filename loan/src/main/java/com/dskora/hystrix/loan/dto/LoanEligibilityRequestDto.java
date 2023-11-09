package com.dskora.hystrix.loan.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class LoanEligibilityRequestDto {
    private BigDecimal balance;

    public LoanEligibilityRequestDto(BigDecimal balance)
    {
        this.balance = balance;
    }
}
