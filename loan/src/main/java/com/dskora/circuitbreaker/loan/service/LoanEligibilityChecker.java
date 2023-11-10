package com.dskora.circuitbreaker.loan.service;

import com.dskora.circuitbreaker.api.vo.EligibleForLoan;
import com.dskora.circuitbreaker.api.vo.LoanEligibility;
import com.dskora.circuitbreaker.api.dto.LoanEligibilityRequestDto;
import com.dskora.circuitbreaker.api.vo.NotEligibleForLoan;
import org.springframework.stereotype.Service;

@Service
public class LoanEligibilityChecker {
    public LoanEligibility check(LoanEligibilityRequestDto loanEligibilityRequestDto)
    {
        if (loanEligibilityRequestDto.getBalance().compareTo(LoanEligibility.MIN_ACCOUNT_BALANCE_FOR_LOAN) < 0) {
            return new NotEligibleForLoan();
        }

        return new EligibleForLoan(loanEligibilityRequestDto.getBalance().add(loanEligibilityRequestDto.getBalance()) , 60);
    }
}
