package com.example.Bank.Service;

import com.example.Bank.dto.LoginRequest;
import com.example.Bank.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public interface UserService {
    public User registerUser(User user);
    public ResponseEntity<?> authenticate(LoginRequest loginRequest);
    public User userDetails(String email);
}


