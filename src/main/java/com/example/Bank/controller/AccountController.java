package com.example.Bank.controller;

import com.example.Bank.Service.AccountService;
import com.example.Bank.Service.TransactionService;
import com.example.Bank.dto.*;
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
    @Autowired
    private TransactionService transactionService;
    @PostMapping("/pin/check")
    public ResponseEntity<?> checkAccountPIN(@RequestBody AccountNumberDTO accountNumberDTO) {
        boolean isPINValid = accountService.isPinCreated(accountNumberDTO.getAccountNumber());

        Map<String, Object> result = new HashMap<>();
        result.put("hasPIN", isPINValid);

        if (isPINValid) {
            result.put("msg", "PIN Created");

        } else {
            result.put("msg", "Pin Not Created");
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/pin/create")
    public ResponseEntity<?> createPIN(@RequestBody PinRequest pinRequest) {
        accountService.createPIN(pinRequest.getAccountNumber(), pinRequest.getPassword(), pinRequest.getPin());

        Map<String, String> response = new HashMap<>();
        response.put("msg", "PIN created successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @PostMapping("/pin/update")
    public ResponseEntity<?> updatePIN(@RequestBody PinUpdateRequest pinUpdateRequest) {
        accountService.updatePIN(pinUpdateRequest.getAccountNumber(), pinUpdateRequest.getOldPin(),
                pinUpdateRequest.getPassword(), pinUpdateRequest.getNewPin());

        Map<String, String> response = new HashMap<>();
        response.put("msg", "PIN updated successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @PostMapping("/deposit")
    public ResponseEntity<?> cashDeposit(@RequestBody AmountRequest amountRequest) {

        if (amountRequest.getAmount() <= 0) {
            Map<String, String> err = new HashMap<>();
            err.put("Error", "Invalid amount");
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }

        accountService.cashDeposit(amountRequest.getAccountNumber(), amountRequest.getPin(), amountRequest.getAmount());
        Map<String, String> response = new HashMap<>();
        response.put("msg", "Cash deposited successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> cashWithdrawal(@RequestBody AmountRequest amountRequest) {
        if (amountRequest.getAmount() <= 0) {
            Map<String, String> err = new HashMap<>();
            err.put("Error", "Invalid amount");
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
        accountService.cashWithdrawal(amountRequest.getAccountNumber(), amountRequest.getPin(), amountRequest.getAmount());
        Map<String, String> response = new HashMap<>();
        response.put("msg", "Cash withdrawn successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/fund-transfer")
    public ResponseEntity<?> fundTransfer(@RequestBody FundTransferRequest fundTransferRequest) {
        if (fundTransferRequest.getAmount() <= 0) {
            Map<String, String> err = new HashMap<>();
            err.put("Error", "Invalid amount");
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
        accountService.fundTransfer(fundTransferRequest.getSourceAccountNumber(), fundTransferRequest.getTargetAccountNumber(),
                fundTransferRequest.getPin(), fundTransferRequest.getAmount());
        Map<String, String> response = new HashMap<>();
        response.put("msg", "Fund transferred successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getAllTransactionsByAccountNumber(@RequestBody AccountNumberDTO accountNumberDTO) {
        List<TransactionDTO> transactions = transactionService
                .getAllTransactionsByAccountNumber(accountNumberDTO.getAccountNumber());
        return ResponseEntity.ok(transactions);
    }
}
