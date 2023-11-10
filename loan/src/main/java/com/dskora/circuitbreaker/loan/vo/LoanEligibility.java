package com.dskora.circuitbreaker.loan.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class LoanEligibility {
    public static BigDecimal MIN_ACCOUNT_BALANCE_FOR_LOAN = BigDecimal.valueOf(1000);

    protected BigDecimal maxAmount;
    protected int maxMonthsTerm;

    protected LoanEligibility(BigDecimal maxAmount, int maxMonthsTerm) {
        this.maxAmount = maxAmount;
        this.maxMonthsTerm = maxMonthsTerm;
    }

    public static LoanEligibility check(BigDecimal balance) {
        LoanEligibility eligibility = balance.compareTo(MIN_ACCOUNT_BALANCE_FOR_LOAN) > 0
            ? new EligibleForLoan(balance.add(balance), 60)
            : new NotEligibleForLoan();

        return eligibility;
    }

    public boolean isEligible()
    {
        return this.maxAmount.compareTo(BigDecimal.ZERO) > 0;
    }
}
