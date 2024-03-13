package com.example.Bank.Service;

import com.example.Bank.Repository.UserRepository;
import com.example.Bank.Service.AccountService;
import com.example.Bank.model.Account;
import com.example.Bank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountService accountService;


    @Override
    public User registerUser(User user) {

        user.setPassword(user.getPassword());

        // Save the user details
        User savedUser = userRepository.save(user);

        // Create an account for the user
        Account account = accountService.createAccount(savedUser);

        savedUser.setAccount(account);
        userRepository.save(savedUser);

        System.out.println(savedUser.getAccount().getAccountNumber());
        System.out.println(account.getUser().getName());
        return savedUser;
    }
}
