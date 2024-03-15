package com.example.Bank.controller;

import com.example.Bank.Service.AccountService;
import com.example.Bank.Service.UserService;
import com.example.Bank.model.Account;
import com.example.Bank.model.User;
import com.example.Bank.util.LoggedinUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @GetMapping("/account")
    public Account AccountDetails(){
        return accountService.AccountDetails(LoggedinUser.getAccountNumber());
    }
    @GetMapping("/user")
    public User UserDetails(){
        return userService.userDetails(LoggedinUser.getAccountNumber());
    }
}
