package com.bank.bankingApp.MSbank.dto;

import com.bank.bankingApp.MSbank.enums.TransactionType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@Builder
public class AccountTransferRequest{

    private TransactionType transactionType;
    private BigDecimal amount;
    private Long fromAccountId;
    private Long toAccountId;
}
