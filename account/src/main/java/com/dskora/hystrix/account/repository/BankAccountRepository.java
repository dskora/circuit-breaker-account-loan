package com.dskora.hystrix.account.repository;

import com.dskora.hystrix.account.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
