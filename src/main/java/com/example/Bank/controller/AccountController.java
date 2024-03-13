package com.example.Bank.controller;

import com.example.Bank.Service.AccountService;
import com.example.Bank.dto.CheckPin;
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
    @PostMapping("/pin/check")
    public ResponseEntity<?> checkAccountPIN(@RequestBody CheckPin checkPin) {
        boolean isPINValid = accountService.isPinCreated(checkPin.getAccount_no());

        Map<String, Object> result = new HashMap<>();
        result.put("hasPIN", isPINValid);

        if (isPINValid) {
            result.put("msg", "PIN Created");

        } else {
            result.put("msg", "Pin Not Created");
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }}
