package com.dskora.circuitbreaker.loan.controller;

import com.dskora.circuitbreaker.loan.dto.EligibleForLoan;
import com.dskora.circuitbreaker.loan.dto.LoanEligibilityRequestDto;
import com.dskora.circuitbreaker.loan.dto.LoanEligibility;
import com.dskora.circuitbreaker.loan.dto.NotEligibleForLoan;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/loans/eligibility")
public class LoanEligibilityController {
    @PostMapping()
    @ResponseStatus(value = OK)
    public LoanEligibility checkEligibility(@RequestBody LoanEligibilityRequestDto loanEligibilityRequest) {
        if (loanEligibilityRequest.getBalance().compareTo(LoanEligibility.MIN_ACCOUNT_BALANCE_FOR_LOAN) < 0) {
            return new NotEligibleForLoan();
        }

        return new EligibleForLoan(loanEligibilityRequest.getBalance().add(loanEligibilityRequest.getBalance()) , 60);
    }
}
