package com.example.Bank.Service;
import com.example.Bank.model.Account;

public class AccountService {

    public interface AccountService {
        public Account AccountDetails(String accountNumber);
    }
}

