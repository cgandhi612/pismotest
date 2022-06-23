package com.test.pismo.controller;

import com.test.pismo.dto.AccountsDTO;
import com.test.pismo.dto.AccountsRequestDTO;
import com.test.pismo.dto.TransactionRequestDTO;
import com.test.pismo.dto.TransactionsDTO;
import com.test.pismo.service.PismoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pismo")
public class TransactionRoutine {

    @Autowired
    PismoService pismoService;

    @PostMapping("/accounts")
    public ResponseEntity<AccountsDTO> createAccount(@RequestBody AccountsRequestDTO accounts) {
       try {
           AccountsDTO acc = pismoService.createAccount(accounts);
           return new ResponseEntity<>(acc, HttpStatus.CREATED);
       }catch (Exception e){
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<AccountsDTO> getAccount(@PathVariable("accountId") Long accountId) {
        try {
            AccountsDTO acc = pismoService.getAccount(accountId);
            return new ResponseEntity<>(acc, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionsDTO> createTransactions(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            TransactionsDTO transactionsDTO = pismoService.createTransactions(transactionRequestDTO);
            return new ResponseEntity<>(transactionsDTO, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
