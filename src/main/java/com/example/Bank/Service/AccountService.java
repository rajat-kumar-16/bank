package com.example.Bank.Service;
import com.example.Bank.model.Account;
import com.example.Bank.model.User;

public interface AccountService {
        public Account createAccount(User user);
        public Account AccountDetails(String accountNumber);
    }