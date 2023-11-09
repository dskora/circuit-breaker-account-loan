package com.dskora.circuitbreaker.account.service;

import com.dskora.circuitbreaker.account.domain.BankAccount;
import com.dskora.circuitbreaker.account.dto.BankAccountDto;
import com.dskora.circuitbreaker.account.dto.CreateAccountDto;
import com.dskora.circuitbreaker.account.repository.BankAccountRepository;
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
        BankAccount bankAccount = this.bankAccountRepository.getById(id);
        BankAccountDto bankAccountDto = this.convert(bankAccount);
        RestTemplate restTemplate = new RestTemplate();
        Map<String, BigDecimal> loanPostData = new HashMap();
        loanPostData.put("balance", BigDecimal.valueOf(2000));
        Map response = restTemplate.postForObject("http://localhost:8081/loans/eligibility", loanPostData, Map.class);
        if (response.get("maxAmount") != null) {
            System.out.println(response);
        }

        return bankAccountDto;
    }

    private BankAccountDto convert(BankAccount account) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(account, BankAccountDto.class);
    }
}
