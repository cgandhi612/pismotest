package com.test.pismo.controller;

import com.test.pismo.Application;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionRoutineTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void createAccounts() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/pismo/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"documentNumber\": \"23456789001\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void createAccountsNegative() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/pismo/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"documentNumber\": \"23456789001\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }
    @Test
    public void getAccounts() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/pismo/account/1").accept(MediaType.APPLICATION_JSON)).andDo(print());
    }

    @Test
    public void getAccountsNegative() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/pismo/account/3").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void createTransaction() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/pismo/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"accountId\": \"1\",\"operationsTypesId\": \"1\",\"amount\": \"50\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void createTransactionNegative() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/pismo/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"accountId\": \"3\",\"operationsTypesId\": \"1\",\"amount\": \"50\" }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

}
