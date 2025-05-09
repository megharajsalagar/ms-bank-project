package com.bank.bankingApp.MSbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    @Column(name = "accountType")
    private String type; // CURRENT, SAVINGS
    private BigDecimal balance;
}
