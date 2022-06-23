package com.test.pismo.service.impl;

import com.test.pismo.dto.AccountsDTO;
import com.test.pismo.dto.AccountsRequestDTO;
import com.test.pismo.dto.TransactionRequestDTO;
import com.test.pismo.dto.TransactionsDTO;
import com.test.pismo.model.Accounts;
import com.test.pismo.model.Transactions;
import com.test.pismo.repository.AccountsRepository;
import com.test.pismo.repository.TransactionsRepository;
import com.test.pismo.service.PismoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PismoServiceImpl implements PismoService {

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AccountsDTO createAccount(AccountsRequestDTO accountsRequestDTO) throws Exception{

        Accounts acc = null;
        if (accountsRequestDTO != null) {
            Accounts accounts = modelMapper.map(accountsRequestDTO, Accounts.class);
            acc = accountsRepository.save(accounts);
        }
        AccountsDTO accountsDTO = modelMapper.map(acc, AccountsDTO.class);
        return accountsDTO;
    }

    @Override
    public AccountsDTO getAccount(Long accountId) throws Exception{
        if(accountId != null)
            return modelMapper.map(accountsRepository.getById(accountId), AccountsDTO.class);
        return null;
    }

    @Override
    public TransactionsDTO createTransactions(TransactionRequestDTO transactionRequestDTO) throws Exception{
        Transactions transactions = null;
        if(transactionRequestDTO != null) {
            Long operationsTypes = transactionRequestDTO.getOperationsTypesId();
            if(operationsTypes == 1 || operationsTypes == 3){
                transactions = modelMapper.map(transactionRequestDTO, Transactions.class);
                transactions.setAmount(transactions.getAmount() * -1);
            }
            Transactions trans = transactionsRepository.save(transactions);
            TransactionsDTO transactionsDTO = modelMapper.map(trans, TransactionsDTO.class);
            return  transactionsDTO;
        }
        return null;
    }
}
