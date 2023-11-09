package com.dskora.hystrix.account;

import com.dskora.hystrix.account.domain.BankAccount;
import com.dskora.hystrix.account.dto.CreateAccountDto;
import com.dskora.hystrix.account.service.BankAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BankAccountIntegrationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;

    @Test
    public void testCreateAccount() throws Exception {
        BankAccount bankAccount = account();

        when(bankAccountService.createAccount(any(CreateAccountDto.class))).thenReturn(bankAccount);
        this.mockMvc
                .perform(post("/accounts")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(new HttpEntity<>(new CreateAccountDto(bankAccount.getFirstname(), bankAccount.getSurname(), bankAccount.getBalance()))))
                )
                .andExpect(jsonPath("firstname").value(bankAccount.getFirstname()))
                .andExpect(jsonPath("surname").value(bankAccount.getSurname()))
                .andExpect(jsonPath("balance").value(bankAccount.getBalance()));
    }

    @Test
    public void testFindAccounts() throws Exception {
        BankAccount bankAccount = account();

        when(bankAccountService.findAccounts()).thenReturn(Arrays.asList(bankAccount));
        this.mockMvc
                .perform(get("/accounts"))
                .andExpect(jsonPath("$[0].firstname").value(bankAccount.getFirstname()))
                .andExpect(jsonPath("$[0].surname").value(bankAccount.getSurname()))
                .andExpect(jsonPath("$[0].balance").value(bankAccount.getBalance()));
    }

    @Test
    public void testFindAccountById() throws Exception {
        BankAccount bankAccount = account();

        when(bankAccountService.findAccountById(any(String.class))).thenReturn(bankAccount);
        this.mockMvc
                .perform(get("/accounts/" + UUID.randomUUID().toString()))
                .andExpect(jsonPath("firstname").value(bankAccount.getFirstname()))
                .andExpect(jsonPath("surname").value(bankAccount.getSurname()))
                .andExpect(jsonPath("balance").value(bankAccount.getBalance()));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static BankAccount account()
    {
        return new BankAccount(UUID.randomUUID(), Faker.instance().name().firstName(), Faker.instance().name().lastName(), BigDecimal.valueOf(Faker.instance().random().nextLong()));
    }
}