package com.example.Bank.controller;

import com.example.Bank.dto.LoginRequest;
import com.example.Bank.dto.UserResponse;
import com.example.Bank.model.User;
import com.example.Bank.Service.UserService;
import com.example.Bank.security.JwtTokenUtil;
import com.example.Bank.util.LoggedinUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
//    private final AuthenticationManager authenticationManager;
//    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                          UserDetailsService userDetailsService) {
        this.userService =  userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setName(registeredUser.getName());
        userResponse.setEmail(registeredUser.getEmail());
        userResponse.setAccountNumber(registeredUser.getAccount().getAccountNumber());
        userResponse.setIFSC_code(registeredUser.getAccount().getIFSC_code());
        userResponse.setBranch(registeredUser.getAccount().getBranch());
        userResponse.setAccount_type(registeredUser.getAccount().getAccount_type());
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest)
    {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getAccountNumber(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid account number or password");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getAccountNumber());
        System.out.println(userDetails);
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> result =  new HashMap<>();
        result.put("token", token);
//        System.out.println(LoggedinUser.getAccountNumber());
        return new ResponseEntity<>(result , HttpStatus.OK);
    }
    @PutMapping ("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateUser = userService.updateUser(user);
        return ResponseEntity.ok(updateUser);
    }

}
