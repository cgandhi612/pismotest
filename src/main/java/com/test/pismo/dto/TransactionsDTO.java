package com.test.pismo.dto;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsDTO {

    private Long transactionId;
    private Long accountId;
    private Long operationsTypesId;
    private Double amount;
    private Date eventDate;

}
