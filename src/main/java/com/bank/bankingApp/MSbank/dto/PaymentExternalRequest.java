package com.bank.bankingApp.MSbank.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@Builder
public class PaymentExternalRequest {

    private Long fromAccountId;
    private BigDecimal amount;
    private String Description;
}
