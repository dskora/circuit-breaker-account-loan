package com.dskora.circuitbreaker.account.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
public class BankAccount {
    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    private String firstname;
    private String surname;
    private BigDecimal balance;

    public BankAccount() {}

    public BankAccount(UUID id, String firstname, String surname, BigDecimal balance) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.balance = balance;
    }
}
