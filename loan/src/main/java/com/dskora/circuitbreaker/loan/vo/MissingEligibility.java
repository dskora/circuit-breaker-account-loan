package com.dskora.circuitbreaker.loan.vo;

import java.math.BigDecimal;

public class MissingEligibility extends LoanEligibility {
    public MissingEligibility() {
        super(BigDecimal.valueOf(0), 0);
    }
}
