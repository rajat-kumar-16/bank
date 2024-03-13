package com.example.Bank.controller;

import com.example.Bank.Service.AccountService;
import com.example.Bank.dto.UpdatePin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

public class AccountController {
    @Autowired
    private AccountService accountService;
    @GetMapping("/pin/check")
    public HttpStatus checkAccountPIN() {
//        boolean isPINValid = accountService.isPinCreated(LoggedinUser.getAccountNumber());
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("hasPIN", isPINValid);
//
//        if (isPINValid) {
//            result.put("msg", "PIN Created");
//
//        } else {
//            result.put("msg", "Pin Not Created");
//        }

        return HttpStatus.OK;
    }}
