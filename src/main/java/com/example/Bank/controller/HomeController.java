package com.example.Bank.controller;

import com.example.Bank.Service.AccountService;
import com.example.Bank.model.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    private AccountService accountService;
    @GetMapping("/account")
    public Account AccountDetails(@RequestBody String account_no){
        return accountService.AccountDetails(account_no);
    }
}
