package com.test.pismo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
    private Long accountId;
    private Long operationsTypesId;
    private Double amount;
}
