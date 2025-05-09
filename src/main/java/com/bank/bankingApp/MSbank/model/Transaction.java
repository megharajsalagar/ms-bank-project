package com.bank.bankingApp.MSbank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    private Long accountId;
    @Column(name = "accountType")
    private String type; // DEPOSIT, PAYMENT, TRANSFER
    private BigDecimal amount;
    private BigDecimal fee;
    private BigDecimal interest;
    private String description;
    private LocalDateTime timestamp;
}
