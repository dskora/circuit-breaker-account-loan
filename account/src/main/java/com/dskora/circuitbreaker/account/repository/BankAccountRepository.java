package com.dskora.circuitbreaker.account.repository;

import com.dskora.circuitbreaker.account.domain.BankAccount;
import com.dskora.circuitbreaker.api.dto.BankAccountDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
}
