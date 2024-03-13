package com.example.Bank.Service;
import com.example.Bank.dto.AmountRequest;
import com.example.Bank.model.Account;
import com.example.Bank.model.User;
import org.springframework.http.ResponseEntity;

public interface AccountService {
        public Account createAccount(User user);
        public Account AccountDetails(String accountNumber);
        public boolean isPinCreated(String accountNumber);
        public ResponseEntity<?> cashDeposit(String accountNumber, String pin,double amount);
}