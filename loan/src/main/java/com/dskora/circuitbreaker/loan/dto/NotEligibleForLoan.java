package com.dskora.circuitbreaker.loan.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotEligibleForLoan implements LoanEligibility {
}
