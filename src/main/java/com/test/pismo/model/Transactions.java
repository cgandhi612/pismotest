package com.test.pismo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transactions implements Serializable {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(name="account_id")
    private Long accountId;

    @Column(name="operations_types")
    private Long operationsTypesId;

    @Column(name="amount")
    private Double amount;

    @CreationTimestamp
    @Column(name="event_date")
    private Date eventDate;
}
