package com.dskora.hystrix.loan.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class EligibleForLoan implements LoanEligibility {
    private final BigDecimal maxAmount;
    private final int maxMonthsTerm;
}
