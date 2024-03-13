package com.example.Bank.controller;

import com.example.Bank.Service.AccountService;
import com.example.Bank.dto.AmountRequest;
import com.example.Bank.dto.UpdatePin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/deposit")
    public ResponseEntity<?> cashDeposit(@RequestBody AmountRequest amountRequest) {

        if (amountRequest.getAmount() <= 0) {
            Map<String, String> err = new HashMap<>();
            err.put("Error", "Invalid amount");
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }

        return accountService.cashDeposit(amountRequest.getAccountNumber(), amountRequest.getPin(), amountRequest.getAmount());
    }
}
