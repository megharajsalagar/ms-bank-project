package com.bank.bankingApp.MSbank.controller;

import com.bank.bankingApp.MSbank.model.Account;
import com.bank.bankingApp.MSbank.model.Customer;
import com.bank.bankingApp.MSbank.payload.ApiResponse;
import com.bank.bankingApp.MSbank.service.AccountService;
import com.bank.bankingApp.MSbank.service.CustomerService;
import com.bank.bankingApp.MSbank.util.ResponseUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final AccountService accountService;

    @PostMapping("/onBoardNewCustomer")
    public ResponseEntity<ApiResponse<Customer>> onBoardNewCustomer(@RequestBody Customer customer){
         customerService.onBoardCustomer(customer);
        return ResponseUtil.created("Customer Details successfully created", customer);
    }

    @GetMapping("/getCustomer/{id}")
    public ResponseEntity<ApiResponse<Customer>> getCustomer(@PathVariable Long id) throws Exception {
        Optional<Customer> findCustomer = customerService.getCustomer(id);
        return findCustomer.map(customer -> ResponseUtil.success("Customer Details fetched successfully", customer)).orElseGet(() -> ResponseUtil.error(HttpStatus.NOT_FOUND, "Customer Not Found", null));
    }

    @GetMapping("/getCustomer/{id}/accounts")
    public ResponseEntity<ApiResponse<List<Account>>> getAccountsOfCustomer(@PathVariable Long id) throws Exception {
        Optional<Customer> findCustomer = customerService.getCustomer(id);
        if(findCustomer.isPresent()){
            return ResponseUtil.success("Customer Details fetched successfully", accountService.getCustomerAccounts(id));
        }
        return ResponseUtil.error(HttpStatus.NOT_FOUND, "Please enter valid customer id", null);
    }
}
