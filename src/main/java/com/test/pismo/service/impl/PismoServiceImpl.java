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

import java.math.BigDecimal;
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
                BigDecimal remaining = new BigDecimal(0);

                if (!accounts.isPresent()) {
                    System.out.println("Account doesn't exist");
                    throw new Exception("Account doesn't exist");
                }

                transactions.setOperationsTypesId(transactionRequestDTO.getOperationsTypesId());
                transactions.setAccountId(transactionRequestDTO.getAccountId());

                if (operationsTypes == 1 || operationsTypes == 3) {
                    transactions.setAmount(transactionRequestDTO.getAmount().negate());
                    transactions.setBalance(transactionRequestDTO.getAmount().negate());
                }else if(operationsTypes == 4){
                    remaining = remaining.add(transactionRequestDTO.getAmount());
                    if(!listTransactions.isEmpty()) {
                        for (int i=0; i<listTransactions.size(); i++){
                            Transactions transactions1 = listTransactions.get(i);
                            if(transactions1 != null && transactions1.getBalance().compareTo(BigDecimal.ZERO) < 0 && remaining.compareTo(BigDecimal.ZERO) > 0 ){
                                BigDecimal balance = transactions1.getBalance();
                                remaining = remaining.add(balance);
                                if(remaining.compareTo(BigDecimal.ZERO) < 0) {
                                    transactions1.setBalance(remaining);
                                    break;
                                }
                                else{
                                    transactions1.setBalance(BigDecimal.ZERO);
                                }
                                saveTransactions.add(transactions1);
                            }
                        }
                        if(!saveTransactions.isEmpty()){
                            transactionsRepository.saveAll(saveTransactions);
                        }
                    }
                    if(remaining.compareTo(BigDecimal.ZERO) < 0){
                        transactions.setAmount(transactionRequestDTO.getAmount());
                        transactions.setBalance(BigDecimal.ZERO);
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
