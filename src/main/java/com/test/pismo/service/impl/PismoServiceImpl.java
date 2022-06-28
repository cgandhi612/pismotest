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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            return modelMapper.map(accountsRepository.findById(accountId), AccountsDTO.class);
        return null;
    }

    @Override
    public TransactionsDTO createTransactions(TransactionRequestDTO transactionRequestDTO) throws Exception{
        Transactions transactions = new Transactions();
        try {
            if (transactionRequestDTO != null) {
                Long operationsTypes = transactionRequestDTO.getOperationsTypesId();
                Optional<Accounts> accounts = accountsRepository.findById(transactionRequestDTO.getAccountId());
                List<Transactions> listTransactions = transactionsRepository.findByAccountId(transactionRequestDTO.getAccountId());
                List<Transactions> saveTransactions = new ArrayList<>();
                Double remaining = 0.0;

                if (!accounts.isPresent()) {
                    System.out.println("Account doesn't exist");
                    throw new Exception("Account doesn't exist");
                }

                transactions.setOperationsTypesId(transactionRequestDTO.getOperationsTypesId());
                transactions.setAccountId(transactionRequestDTO.getAccountId());

                if (operationsTypes == 1 || operationsTypes == 3) {
                    transactions.setAmount(transactionRequestDTO.getAmount() * -1);
                    transactions.setBalance(transactionRequestDTO.getAmount() * -1);
                }else if(operationsTypes == 4){
                    remaining = remaining + transactionRequestDTO.getAmount();
                    if(!listTransactions.isEmpty()) {
                        for (int i=0; i<listTransactions.size(); i++){
                            Transactions transactions1 = listTransactions.get(i);
                            if(transactions1 != null && transactions1.getAmount() < 0 && remaining > 0 ){
                                Double balance = transactions1.getBalance();
                                remaining = remaining + balance;
                                if(remaining < 0) {
                                    transactions1.setBalance(remaining);
                                    break;
                                }
                                else{
                                    transactions1.setBalance(0.0);
                                }
                                saveTransactions.add(transactions1);
                            }
                        }
                        if(!saveTransactions.isEmpty()){
                            transactionsRepository.saveAll(saveTransactions);
                        }
                    }
                    if(remaining < 0){
                        transactions.setAmount(transactionRequestDTO.getAmount());
                        transactions.setBalance(0.0);
                    }else{
                        transactions.setAmount(transactionRequestDTO.getAmount());
                        transactions.setBalance(remaining);
                    }
                }

                Transactions trans = transactionsRepository.save(transactions);
                TransactionsDTO transactionsDTO = modelMapper.map(trans, TransactionsDTO.class);
                return transactionsDTO;
            }
        }catch(Exception e){
            throw e;
        }
        return null;
    }
}
