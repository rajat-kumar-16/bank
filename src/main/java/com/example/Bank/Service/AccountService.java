package com.example.Bank.Service;
import com.example.Bank.dto.AmountRequest;
import com.example.Bank.dto.TransactionDTO;
import com.example.Bank.model.Account;
import com.example.Bank.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
        public Account createAccount(User user);
        public Account AccountDetails(String accountNumber);
        public boolean isPinCreated(String accountNumber);
        public void createPIN(String accountNumber, String password, String pin) ;
        public void updatePIN(String accountNumber, String oldPIN, String password, String newPIN);
        public void cashDeposit(String accountNumber, String pin,double amount);
        public void cashWithdrawal(String accountNumber, String pin, double amount);
        public void fundTransfer(String sourceAccountNumber, String targetAccountNumber, String pin, double amount);

}