package com.example.Bank.Service;

import com.example.Bank.Repository.AccountRepository;
import com.example.Bank.model.Account;
import com.example.Bank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

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
}
