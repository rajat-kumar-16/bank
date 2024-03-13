package com.example.Bank.Service;

import com.example.Bank.Repository.AccountRepository;
import com.example.Bank.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account AccountDetails(String account_no){
        System.out.println("2");
        return null;
    }
}
