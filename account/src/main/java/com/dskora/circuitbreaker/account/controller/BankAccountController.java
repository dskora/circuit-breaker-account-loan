package com.dskora.circuitbreaker.account.controller;

import com.dskora.circuitbreaker.account.domain.BankAccount;
import com.dskora.circuitbreaker.account.dto.CreateAccountDto;
import com.dskora.circuitbreaker.account.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {
    @Autowired
    BankAccountService accountService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<BankAccount> getAccounts() {
        return this.accountService.findAccounts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BankAccount getAccount(@PathVariable("id") String id) {
        return this.accountService.findAccountById(id);
    }

    @PostMapping()
    @ResponseStatus(value = CREATED)
    public BankAccount createAccount(@RequestBody CreateAccountDto createAccountDto) {
        return this.accountService.createAccount(createAccountDto);
    }
}