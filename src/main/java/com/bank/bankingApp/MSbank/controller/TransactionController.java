package com.bank.bankingApp.MSbank.controller;


import com.bank.bankingApp.MSbank.model.Transaction;
import com.bank.bankingApp.MSbank.payload.ApiResponse;
import com.bank.bankingApp.MSbank.service.TransactionService;
import com.bank.bankingApp.MSbank.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse<List<Transaction>>> getTransactions(@PathVariable Long accountId){
            List<Transaction> transactions= transactionService.getTransactions(accountId);
            return  ResponseUtil.success("Transaction Details fetched successfully",transactions);
    }
}
