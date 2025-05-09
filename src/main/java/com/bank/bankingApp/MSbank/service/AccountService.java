package com.bank.bankingApp.MSbank.service;

import com.bank.bankingApp.MSbank.dto.AccountTransferRequest;
import com.bank.bankingApp.MSbank.dto.PaymentExternalRequest;
import com.bank.bankingApp.MSbank.model.Account;
import com.bank.bankingApp.MSbank.model.Customer;
import com.bank.bankingApp.MSbank.model.Transaction;
import com.bank.bankingApp.MSbank.repository.AccountRepository;
import com.bank.bankingApp.MSbank.repository.CustomerRepository;
import com.bank.bankingApp.MSbank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;


    public List<Account> getCustomerAccounts(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }


    public void transferBetweenAccounts(AccountTransferRequest request){
     Account from = accountRepository.findById(request.getFromAccountId()).orElseThrow(
             ()-> new IllegalArgumentException("From Account not found")
     );
     Account to = accountRepository.findById(request.getToAccountId()).orElseThrow(
             ()-> new IllegalArgumentException("To Account not found")
     );

     BigDecimal amount = request.getAmount();

     if(amount.compareTo(BigDecimal.ZERO) <=0){
         throw new IllegalArgumentException("Amount must be greater than zero");
     }

     if(!"CURRENT".equalsIgnoreCase(from.getType())){
         throw new IllegalArgumentException("Only CURRENT account can initiate the transfers");
     }

     if(from.getBalance().compareTo(amount) < 0){
         throw new IllegalArgumentException("Insufficient balance in source account");
     }

     BigDecimal fee = amount.multiply(new BigDecimal("0.0005"));
     BigDecimal totalDebit = amount.add(fee);

     if(from.getBalance().compareTo(totalDebit) < 0){
         throw new IllegalArgumentException("Insufficient balance including fee");
     }

     // Debit from source
     from.setBalance(from.getBalance().subtract(totalDebit));

     // Apply 0.5% interest if money is credited to a SAVINGS account
     if("SAVING".equalsIgnoreCase(to.getType())){
         BigDecimal updatedBalance = to.getBalance();
         BigDecimal interest = updatedBalance.multiply(new BigDecimal("0.005")); //0.5%
         to.setBalance(updatedBalance.add(interest));

         //Record interest as a seperate transaction
         Transaction interestTx = new Transaction();
         interestTx.setAccountId(to.getId());
         interestTx.setType("DEPOSIT");
         interestTx.setAmount(interest);
         interestTx.setFee(BigDecimal.ZERO);
         interestTx.setInterest(interest);
         interestTx.setDescription("Interest credited on transfer");
         interestTx.setTimestamp(LocalDateTime.now());

         transactionRepository.save(interestTx);
     }

     // Credit to target
     to.setBalance(to.getBalance().add(amount));



     // Save updated balances
     accountRepository.save(from);
     accountRepository.save(to);


     // Record transactions (optional)
        Transaction debitTx = new Transaction();
        debitTx.setAccountId(from.getId());
        debitTx.setType("TRANSFER_OUT");
        debitTx.setAmount(amount.negate());
        debitTx.setFee(fee);
        debitTx.setDescription("Transfer to account " + to.getId() + "(Fee:"+fee+")");
        debitTx.setTimestamp(LocalDateTime.now());


        Transaction creditTx = new Transaction();
        creditTx.setAccountId(to.getId());
        creditTx.setType("TRANSFER_IN");
        creditTx.setAmount(amount.negate());
        creditTx.setDescription("Transfer from account " + from.getId());
        creditTx.setTimestamp(LocalDateTime.now());

        transactionRepository.save(debitTx);
        transactionRepository.save(creditTx);
    }

    public void makeExternalPayment(PaymentExternalRequest paymentExternalRequest) {
        Account from = accountRepository.findById(paymentExternalRequest.getFromAccountId()).orElseThrow( ()-> new IllegalArgumentException("From account Id not found"));
        BigDecimal amount = paymentExternalRequest.getAmount();


        if(amount.compareTo(BigDecimal.ZERO) <=0){
            throw new IllegalArgumentException("Amount must be greater than ZERO");
        }

        if(!"CURRENT".equalsIgnoreCase(from.getType())){
            throw new IllegalArgumentException("Only CURRENT account can initiate the transfers");
        }

        if(from.getBalance().compareTo(amount) <0){
            throw new IllegalArgumentException("Insufficient balance in source(from) account");
        }

        //Add 0.05% charge
        BigDecimal fee = amount.multiply(new BigDecimal("0.005")); // 0.05%
        BigDecimal totalDebit = amount.add(fee);

        if(from.getBalance().compareTo(totalDebit) <0){
            throw new IllegalArgumentException("Insufficient funds including fee in source account");
        }

        from.setBalance(from.getBalance().subtract(totalDebit));
        accountRepository.save(from);

        //Record the transaction
        Transaction tx = new Transaction();
        tx.setAccountId(from.getId());
        tx.setAmount(amount.negate());
        tx.setType("PAYMENT");
        tx.setFee(fee);
        tx.setInterest(BigDecimal.ZERO);
        tx.setDescription("Transaction from"+from.getId()+"successful");
        tx.setTimestamp(LocalDateTime.now());
        transactionRepository.save(tx);
    }
}
