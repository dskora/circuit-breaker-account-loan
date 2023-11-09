package com.dskora.circuitbreaker.account.repository;

import com.dskora.circuitbreaker.account.domain.BankAccount;
import com.dskora.circuitbreaker.account.dto.BankAccountDto;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
}
