package com.dskora.hystrix.account.service;

import com.dskora.hystrix.account.domain.BankAccount;
import com.dskora.hystrix.account.dto.CreateAccountDto;
import com.dskora.hystrix.account.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;

    public List<BankAccount> findAccounts() {
        return this.bankAccountRepository.findAll();
    }

    public BankAccount createAccount(CreateAccountDto creationDto) {
        BankAccount bankAccount = new BankAccount(UUID.randomUUID(), creationDto.getFirstname(), creationDto.getSurname(), creationDto.getInitialBalance());
        this.bankAccountRepository.save(bankAccount);

        return bankAccount;
    }

    public BankAccount findAccountById(String id) {
        return this.bankAccountRepository.getById(id);
    }
}
