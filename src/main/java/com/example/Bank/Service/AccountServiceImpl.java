package com.example.Bank.Service;

import com.example.Bank.Repository.AccountRepository;
import com.example.Bank.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

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
