package com.bank.bankingApp.MSbank.controller;

import com.bank.bankingApp.MSbank.dto.AccountTransferRequest;
import com.bank.bankingApp.MSbank.dto.PaymentExternalRequest;
import com.bank.bankingApp.MSbank.payload.ApiResponse;
import com.bank.bankingApp.MSbank.service.AccountService;
import com.bank.bankingApp.MSbank.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<AccountTransferRequest>> transferBetweenAccounts(@RequestBody AccountTransferRequest request){
        try{
            accountService.transferBetweenAccounts(request);
            return ResponseUtil.created("Transfer Successfully Completed", request);
        }catch(IllegalArgumentException e){
            return ResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/externalPayment")
    public ResponseEntity<ApiResponse<PaymentExternalRequest>> externalPayment(@RequestBody PaymentExternalRequest paymentExternalRequest){
        try{
            accountService.makeExternalPayment(paymentExternalRequest);
            return ResponseUtil.created("Transfer Successfully Completed", paymentExternalRequest);
        }catch(IllegalArgumentException e){
            return ResponseUtil.error(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }


}
