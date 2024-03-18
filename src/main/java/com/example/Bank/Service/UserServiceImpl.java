package com.example.Bank.Service;

import com.example.Bank.Repository.AccountRepository;
import com.example.Bank.Repository.UserRepository;
import com.example.Bank.Service.AccountService;
import com.example.Bank.dto.LoginRequest;
import com.example.Bank.exception.EmailAlreadyExist;
import com.example.Bank.exception.UserValidation;
import com.example.Bank.model.Account;
import com.example.Bank.model.User;
import com.example.Bank.util.LoggedinUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, AccountService accountService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.passwordEncoder =  passwordEncoder;
    }
    @Override
    public User registerUser(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Save the user details
        if(userRepository.findByEmail(user.getEmail())!=null){
            throw new EmailAlreadyExist("Email already exists");
        }
        User savedUser = userRepository.save(user);
        // Create an account for the user
        Account account = accountService.createAccount(savedUser);

        savedUser.setAccount(account);
        userRepository.save(savedUser);

//        System.out.println(savedUser.getAccount().getAccountNumber());
//        System.out.println(account.getUser().getName());
        return savedUser;
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findByAccountAccountNumber(LoggedinUser.getAccountNumber());

        if(user.getEmail() != null){
            if(user.getEmail().isEmpty())
                throw new UserValidation("Email can't be empty");
            else
                if(userRepository.findByEmail(user.getEmail())!=null && userRepository.findByEmail(user.getEmail())!=existingUser){
                    throw new EmailAlreadyExist("Email already exists");
                }
                existingUser.setEmail(user.getEmail());
        }
        if(user.getName() != null){
            if(user.getName().isEmpty())
                throw new UserValidation("Name can't be empty");
            else
                existingUser.setName(user.getName());
        }
        if(user.getPhone_number() != null){
            if(user.getPhone_number().isEmpty())
                throw new UserValidation("Phone number can't be empty");
            else
                existingUser.setPhone_number(user.getPhone_number());
        }
        if(user.getAddress() != null){
            existingUser.setAddress(user.getAddress());
        }
        return userRepository.save(existingUser);
    }
    @Override
    public User userDetails(String accountNumber) {
        return userRepository.findByAccountAccountNumber(accountNumber);
    }
}
