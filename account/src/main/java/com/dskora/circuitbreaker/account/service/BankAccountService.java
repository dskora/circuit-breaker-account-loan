package com.dskora.circuitbreaker.account.service;

import com.dskora.circuitbreaker.account.domain.BankAccount;
import com.dskora.circuitbreaker.account.repository.BankAccountRepository;
import com.dskora.circuitbreaker.api.dto.BankAccountDto;
import com.dskora.circuitbreaker.api.dto.CreateAccountDto;
import com.dskora.circuitbreaker.api.dto.LoanEligibilityRequestDto;
import com.dskora.circuitbreaker.api.vo.LoanEligibility;
import com.dskora.circuitbreaker.api.vo.MissingEligibility;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;

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
