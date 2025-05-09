package com.bank.bankingApp.MSbank.service;

import com.bank.bankingApp.MSbank.model.Transaction;
import com.bank.bankingApp.MSbank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {


    private final TransactionRepository transactionRepository;
    public List<Transaction> getTransactions(Long accountId){
        return transactionRepository.findByAccountId(accountId);
    }
}
