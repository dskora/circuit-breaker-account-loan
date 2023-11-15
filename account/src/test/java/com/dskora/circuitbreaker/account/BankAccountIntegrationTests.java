package com.dskora.circuitbreaker.account;

import com.dskora.circuitbreaker.account.controller.BankAccountController;
import com.dskora.circuitbreaker.api.dto.BankAccountDto;
import com.dskora.circuitbreaker.account.service.BankAccountService;
import com.dskora.circuitbreaker.api.dto.CreateAccountDto;
import com.dskora.circuitbreaker.api.vo.NotEligibleForLoan;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

@WebMvcTest(controllers = BankAccountController.class)
public class BankAccountIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;

    @Test
    public void testCreateAccount() throws Exception {
        BankAccountDto bankAccount = account();

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
        BankAccountDto bankAccount = account();

        when(bankAccountService.findAccounts()).thenReturn(Arrays.asList(bankAccount));
        this.mockMvc
                .perform(get("/accounts"))
                .andExpect(jsonPath("$[0].firstname").value(bankAccount.getFirstname()))
                .andExpect(jsonPath("$[0].surname").value(bankAccount.getSurname()))
                .andExpect(jsonPath("$[0].balance").value(bankAccount.getBalance()));
    }

    @Test
    public void testFindAccountById() throws Exception {
        BankAccountDto bankAccount = account();

        when(bankAccountService.findAccountById(any(UUID.class))).thenReturn(bankAccount);
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

    private static BankAccountDto account()
    {
        return new BankAccountDto(UUID.randomUUID(), Faker.instance().name().firstName(), Faker.instance().name().lastName(), BigDecimal.valueOf(Faker.instance().random().nextLong()), new NotEligibleForLoan());
    }
}