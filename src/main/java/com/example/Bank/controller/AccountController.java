package com.example.Bank.controller;

import com.example.Bank.Service.AccountService;
import com.example.Bank.dto.UpdatePin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AccountController {
    @Autowired
    private AccountService accountService;
//    @PostMapping("/pin/update")
//    public HttpStatus UpdatePin(@RequestBody UpdatePin updatePin){
//
//    }
}
