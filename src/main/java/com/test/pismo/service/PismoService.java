package com.test.pismo.service;

import com.test.pismo.dto.AccountsDTO;
import com.test.pismo.dto.AccountsRequestDTO;
import com.test.pismo.dto.TransactionRequestDTO;
import com.test.pismo.dto.TransactionsDTO;
import com.test.pismo.model.Accounts;
import com.test.pismo.model.Transactions;

public interface PismoService {

    AccountsDTO createAccount(AccountsRequestDTO accountsRequestDTO) throws Exception;
    AccountsDTO getAccount(Long accountId) throws Exception;
    TransactionsDTO createTransactions(TransactionRequestDTO transactionRequestDTO) throws Exception;
}
