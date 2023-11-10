package com.dskora.circuitbreaker.api.vo;

import java.math.BigDecimal;

public class NotEligibleForLoan extends LoanEligibility {
    public NotEligibleForLoan() {
        super(BigDecimal.valueOf(0), 0);
    }
}
