package com.bank.bankingApp.MSbank.service;

import com.bank.bankingApp.MSbank.model.Account;
import com.bank.bankingApp.MSbank.model.Customer;
import com.bank.bankingApp.MSbank.repository.AccountRepository;
import com.bank.bankingApp.MSbank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    public void onBoardCustomer(Customer customer){
        customerRepository.save(customer);
        Account account = Account.builder().customerId(customer.getId()).type("CURRENT").balance(new BigDecimal("1000.00")).build();
        Account savings = Account.builder().customerId(customer.getId()).type("SAVING").balance(new BigDecimal("500.00")).build();
        accountRepository.saveAll(List.of(account,savings));
    }

    public Optional<Customer> getCustomer(Long id) {
        return customerRepository.findById(id);
    }

}
