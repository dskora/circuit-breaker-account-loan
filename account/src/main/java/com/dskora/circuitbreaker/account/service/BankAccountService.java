package com.dskora.circuitbreaker.account.service;

import com.dskora.circuitbreaker.account.domain.BankAccount;
import com.dskora.circuitbreaker.account.dto.BankAccountDto;
import com.dskora.circuitbreaker.account.dto.CreateAccountDto;
import com.dskora.circuitbreaker.account.repository.BankAccountRepository;
import com.dskora.circuitbreaker.loan.dto.LoanEligibilityRequestDto;
import com.dskora.circuitbreaker.loan.vo.LoanEligibility;
import com.dskora.circuitbreaker.loan.vo.MissingEligibility;
import com.dskora.circuitbreaker.loan.vo.NotEligibleForLoan;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;


    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    public List<BankAccountDto> findAccounts() {
        return this.bankAccountRepository.findAll()
            .stream()
            .map(account -> this.convert(account))
            .collect(Collectors.toList());
    }

    public BankAccountDto createAccount(CreateAccountDto creationDto) {
        BankAccount bankAccount = new BankAccount(UUID.randomUUID(), creationDto.getFirstname(), creationDto.getSurname(), creationDto.getInitialBalance());
        this.bankAccountRepository.save(bankAccount);

        return this.convert(bankAccount);
    }

    public BankAccountDto findAccountById(UUID id) {
        return this.convert(this.bankAccountRepository.getById(id));
    }

    @CircuitBreaker(name="loanEligibilityCheck", fallbackMethod = "loanEligibilityServiceFallback")
    public LoanEligibility getEligibility(BigDecimal balance) {
        return new RestTemplate().postForObject("http://localhost:8081/loans/eligibility", new LoanEligibilityRequestDto(balance), LoanEligibility.class);
    }

    private LoanEligibility loanEligibilityServiceFallback(Exception e) {
        return new MissingEligibility();
    }

    private BankAccountDto convert(BankAccount account) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(account, BankAccountDto.class);
    }
}
