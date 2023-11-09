package com.dskora.circuitbreaker.loan.controller;

import com.dskora.circuitbreaker.loan.dto.LoanEligibilityRequestDto;
import com.dskora.circuitbreaker.loan.vo.LoanEligibility;
import com.dskora.circuitbreaker.loan.service.LoanEligibilityChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/loans/eligibility")
public class LoanEligibilityController {
    @Autowired
    LoanEligibilityChecker loanEligibilityChecker;

    @PostMapping()
    @ResponseStatus(value = OK)
    public LoanEligibility checkEligibility(@RequestBody LoanEligibilityRequestDto loanEligibilityRequest) {
        return LoanEligibility.check(loanEligibilityRequest.getBalance());
    }
}
