package com.dskora.circuitbreaker.api.vo;

import java.math.BigDecimal;

public class EligibleForLoan extends LoanEligibility {
    public EligibleForLoan(BigDecimal maxAmount, int maxMonthsTerm) {
        super(maxAmount, maxMonthsTerm);
    }
}
