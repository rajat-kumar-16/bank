package com.example.Bank.controller;

import com.example.Bank.Service.AccountService;
import com.example.Bank.Service.UserService;
import com.example.Bank.dto.AccountNumberDTO;
import com.example.Bank.dto.UserEmailDTO;
import com.example.Bank.model.Account;
import com.example.Bank.model.User;
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
    public Account AccountDetails(@RequestBody AccountNumberDTO accountNumberDTO){
        return accountService.AccountDetails(accountNumberDTO.getAccountNumber());
    }
    @GetMapping("/user")
    public User UserDetails(@RequestBody UserEmailDTO userEmailDTO){
        return userService.userDetails(userEmailDTO.getEmail());
    }
}
