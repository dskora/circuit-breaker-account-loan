package com.dskora.circuitbreaker.loan.controller;

import com.dskora.circuitbreaker.api.dto.LoanEligibilityRequestDto;
import com.dskora.circuitbreaker.api.vo.LoanEligibility;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/loans/eligibility")
public class LoanEligibilityController {
    @PostMapping()
    @ResponseStatus(value = OK)
    public LoanEligibility checkEligibility(@RequestBody LoanEligibilityRequestDto loanEligibilityRequest) {
        return LoanEligibility.check(loanEligibilityRequest.getBalance());
    }
}
