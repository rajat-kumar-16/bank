package com.example.Bank.Service;

import com.example.Bank.Repository.AccountRepository;
import com.example.Bank.Repository.UserRepository;
import com.example.Bank.Service.AccountService;
import com.example.Bank.dto.LoginRequest;
import com.example.Bank.exception.UserValidation;
import com.example.Bank.model.Account;
import com.example.Bank.model.User;
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
        User savedUser = userRepository.save(user);

        // Create an account for the user
        Account account = accountService.createAccount(savedUser);

        savedUser.setAccount(account);
        userRepository.save(savedUser);

        System.out.println(savedUser.getAccount().getAccountNumber());
        System.out.println(account.getUser().getName());
        return savedUser;
    }

    @Override
    public ResponseEntity<?> authenticate(LoginRequest loginRequest) {
        if(accountRepository.findByAccountNumber(loginRequest.getAccountNumber())==null || !passwordEncoder.matches(loginRequest.getPassword(),accountRepository.findByAccountNumber(loginRequest.getAccountNumber()).getUser().getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid account number or password");
        }
        User user=accountRepository.findByAccountNumber(loginRequest.getAccountNumber()).getUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public User updateUser(User user) {
        System.out.println(user.getEmail());
        User existingUser = userRepository.findByEmail("xyz@gmail.com");

        if (existingUser == null) {
            System.out.println("User not found with email: xyz@gmail.com");
        } else {
            System.out.println("User found: " + existingUser.getEmail());
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
    public User userDetails(String email) {
        User user = userRepository.findByEmail(email);
        if(user==null){
            System.out.println("account with this email is not there"+ email);
            return null;
        }
        return user;
    }
}
