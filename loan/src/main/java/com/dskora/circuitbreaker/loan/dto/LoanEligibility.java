package com.dskora.circuitbreaker.loan.dto;

import java.math.BigDecimal;

public interface LoanEligibility {
    BigDecimal MIN_ACCOUNT_BALANCE_FOR_LOAN = BigDecimal.valueOf(1000);
}
