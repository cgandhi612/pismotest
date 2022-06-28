package com.test.pismo.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
    private Long accountId;
    private Long operationsTypesId;
    private BigDecimal amount;
}
