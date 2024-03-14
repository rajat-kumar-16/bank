package com.example.Bank.Service;

import com.example.Bank.Repository.AccountRepository;
import com.example.Bank.exception.InsufficientBalanceException;
import com.example.Bank.exception.NotFoundException;
import com.example.Bank.Repository.TransactionRepository;
import com.example.Bank.exception.UnauthorizedException;
import com.example.Bank.model.Account;
import com.example.Bank.model.Transaction;
import com.example.Bank.model.TransactionType;
import com.example.Bank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public Account createAccount(User user) {
        String accountNumber = generateUniqueAccountNumber();
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(0.0);
        return accountRepository.save(account);
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            // Generate a UUID as the account number
            accountNumber = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        } while (accountRepository.findByAccountNumber(accountNumber) != null);

        return accountNumber;
    }


    @Override
    public Account AccountDetails(String account_no) {
        Account account = accountRepository.findByAccountNumber(account_no);
        if(account==null){
            System.out.println("account with this account no. not there"+account_no);
            return null;
        }
        return account;
    }

    @Override
    public boolean isPinCreated(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        System.out.println(account);
        if(account==null)
        {
            System.out.println("Account Not Found");
            return false;
        }

        if(account.getPin()== null)
            return false;
        return true;
    }

    @Override
    public void createPIN(String accountNumber, String password, String pin) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new NotFoundException("Account not found");
        }

        if(!account.getUser().getPassword().equals(password)){
            throw new UnauthorizedException("Invalid password");

        }

        account.setPin(pin);
        accountRepository.save(account);
    }

    @Override
    public void updatePIN(String accountNumber, String oldPIN, String password, String newPIN) {
        System.out.println(accountNumber+"  "+oldPIN+" "+newPIN+"  "+password);

        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new NotFoundException("Account not found");
        }

        if(!account.getUser().getPassword().equals(password)){
            throw new UnauthorizedException("Invalid password");

        }

        if(!account.getPin().equals(oldPIN)){
            throw new UnauthorizedException("Invalid pin");

        }

        account.setPin(newPIN);
        accountRepository.save(account);
    }

    @Override
    public void cashDeposit(String accountNumber, String pin, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if(account==null){
            throw new NotFoundException("Account not found");
        }
        if(!account.getPin().equals(pin)){
            throw new UnauthorizedException("Invalid PIN");
        }
        double currentBalance = account.getBalance();
        double newBalance = currentBalance + amount;
        account.setBalance(newBalance);
        accountRepository.save(account);
        Transaction transaction =  new Transaction();
        transaction.setAmount(amount);
        transaction.setSourceAccount(account);
        transaction.setTransactionType(TransactionType.CASH_DEPOSIT);
        transaction.setTransaction_date(new Date());
        transactionRepository.save(transaction);
    }

    @Override
    public void cashWithdrawal(String accountNumber, String pin, double amount) {
        Account account =accountRepository.findByAccountNumber(accountNumber);
        if(account==null){
            throw new NotFoundException("Account not found");
        }
        if(!account.getPin().equals(pin)){
            throw new UnauthorizedException("Invalid PIN");
        }
        double currentBalance = account.getBalance();
        if (currentBalance < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        double newBalance = currentBalance - amount;
        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction =  new Transaction();
        transaction.setAmount(amount);
        transaction.setSourceAccount(account);
        transaction.setTransactionType(TransactionType.CASH_WITHDRAWAL);
        transaction.setTransaction_date(new Date());
        transactionRepository.save(transaction);
    }

    @Override
    public void fundTransfer(String sourceAccountNumber, String targetAccountNumber, String pin, double amount) {
        Account sourceAccount =accountRepository.findByAccountNumber(sourceAccountNumber);
        if(sourceAccount == null){
            throw new NotFoundException("Source account not found");
        }
        Account targetAccount = accountRepository.findByAccountNumber(targetAccountNumber);
        if(targetAccount == null){
            throw new NotFoundException("Target account not found");
        }
        if(!sourceAccount.getPin().equals(pin)){
            throw new UnauthorizedException("Invalid PIN");
        }
        double sourceBalance = sourceAccount.getBalance();
        if (sourceBalance < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        double newSourceBalance = sourceBalance - amount;
        sourceAccount.setBalance(newSourceBalance);
        accountRepository.save(sourceAccount);

        double targetBalance = targetAccount.getBalance();
        double newTargetBalance = targetBalance + amount;
        targetAccount.setBalance(newTargetBalance);
        accountRepository.save(targetAccount);

        Transaction transaction = new Transaction();
        transaction.setTransaction_date(new Date());
        transaction.setSourceAccount(sourceAccount);
        transaction.setTargetAccount(targetAccount);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.CASH_TRANSFER);
        transactionRepository.save(transaction);
    }
}
