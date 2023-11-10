package com.dskora.circuitbreaker.api.vo;

import java.math.BigDecimal;

public class MissingEligibility extends LoanEligibility {
    public MissingEligibility() {
        super(BigDecimal.valueOf(0), 0);
    }
}
