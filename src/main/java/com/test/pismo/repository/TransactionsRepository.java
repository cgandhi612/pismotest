package com.test.pismo.repository;

import com.test.pismo.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    List<Transactions> findByAccountId(Long accountId);
}
