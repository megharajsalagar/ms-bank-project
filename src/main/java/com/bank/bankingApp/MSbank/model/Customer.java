package com.bank.bankingApp.MSbank.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
}
