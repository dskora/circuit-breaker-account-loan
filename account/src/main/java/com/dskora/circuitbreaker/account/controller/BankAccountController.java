package com.dskora.circuitbreaker.account.controller;

import com.dskora.circuitbreaker.account.service.BankAccountService;
import com.dskora.circuitbreaker.api.dto.BankAccountDto;
import com.dskora.circuitbreaker.api.dto.CreateAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {
    @Autowired
    BankAccountService accountService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<BankAccountDto> getAccounts() {
        return this.accountService.findAccounts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BankAccountDto getAccount(@PathVariable("id") String id) {
        BankAccountDto bankAccountDto = this.accountService.findAccountById(UUID.fromString(id));
        bankAccountDto.setLoanEligibility(this.accountService.getEligibility(bankAccountDto.getBalance()));

        return bankAccountDto;
    }

    @PostMapping()
    @ResponseStatus(value = CREATED)
    public BankAccountDto createAccount(@RequestBody CreateAccountDto createAccountDto) {
        return this.accountService.createAccount(createAccountDto);
    }
}