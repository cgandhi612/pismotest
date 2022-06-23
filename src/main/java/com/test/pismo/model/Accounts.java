package com.test.pismo.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Accounts implements Serializable {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "document_number", unique = true)
    private Long documentNumber;

    public Accounts() {}

    public Accounts(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

}
