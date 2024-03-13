package com.example.Bank.controller;

import com.example.Bank.Service.AccountService;
import com.example.Bank.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home/account")
public class HomeController {
    @Autowired
    private AccountService accountService;
    @GetMapping("/{account_no}")
    public Account AccountDetails(@PathVariable String account_no){
        return accountService.AccountDetails(account_no);
    }


}
