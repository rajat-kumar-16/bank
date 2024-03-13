package com.example.Bank.Service;

import com.example.Bank.Repository.AccountRepository;
import com.example.Bank.Repository.UserRepository;
import com.example.Bank.Service.AccountService;
import com.example.Bank.dto.LoginRequest;
import com.example.Bank.model.Account;
import com.example.Bank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
//    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
//        this.passwordEncoder =  passwordEncoder;
    }
    @Override
    public User registerUser(User user) {

//        String encodedPassword = passwordEncoder.encode(user.getPassword());
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

    @Override
    public ResponseEntity<?> authenticate(LoginRequest loginRequest) {
        if(accountRepository.findByAccountNumber(loginRequest.getAccountNumber())==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found.");
        }
        if(!accountRepository.findByAccountNumber(loginRequest.getAccountNumber()).getUser().getPassword().equals(loginRequest.getPassword())){
            System.out.println(loginRequest.getAccountNumber());
            System.out.println(accountRepository.findByAccountNumber(loginRequest.getAccountNumber()).getUser().getPassword());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong credentials");
        }
        User user=accountRepository.findByAccountNumber(loginRequest.getAccountNumber()).getUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
