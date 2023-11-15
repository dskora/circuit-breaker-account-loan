package com.dskora.circuitbreaker.loan;

import com.dskora.circuitbreaker.api.vo.LoanEligibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LoanIntegrationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoanEligibility() throws Exception {
        this.mockMvc
            .perform(post("/loans/eligibility")
                .contentType(APPLICATION_JSON)
                .content("{\"balance\": \"" + LoanEligibility.MIN_ACCOUNT_BALANCE_FOR_LOAN.add(BigDecimal.valueOf(1)).toString() + "\"}")
            )
            .andExpect(jsonPath("eligible").value(true));

        this.mockMvc
            .perform(post("/loans/eligibility")
                .contentType(APPLICATION_JSON)
                .content("{\"balance\": \"" + LoanEligibility.MIN_ACCOUNT_BALANCE_FOR_LOAN.subtract(BigDecimal.valueOf(1)).toString() + "\"}")
            )
            .andExpect(jsonPath("eligible").value(false));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
