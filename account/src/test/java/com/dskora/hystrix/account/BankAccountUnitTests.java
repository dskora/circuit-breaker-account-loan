package com.dskora.hystrix.account;

import com.dskora.hystrix.account.dto.CreateAccountDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BankAccountUnitTests {
    @Test
    public void testAccountRequest() {
        String firstname = "Dan";
        String surname = "Skora";
        BigDecimal balance = BigDecimal.valueOf(100);

        CreateAccountDto request = new CreateAccountDto(firstname, surname, balance);
        assertEquals(request.getFirstname(), firstname);
        assertEquals(request.getSurname(), surname);
        assertEquals(request.getInitialBalance(), balance);
    }
}