package com.test.pismo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "operations_types")
@Getter
@Setter
public class OperationsTypes implements Serializable {

    @Id
    @Column(name = "operationtype_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationsTypesId;

    @Column(name = "description")
    private String description;
}
