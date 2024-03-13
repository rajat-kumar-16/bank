package com.example.Bank.Service;

import com.example.Bank.Repository.AdminRepository;
import com.example.Bank.dto.AdminLoginRequest;
import com.example.Bank.dto.LoginRequest;
import com.example.Bank.model.Admin;
import com.example.Bank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin loginAdmin(String username, String password) {
        // Retrieve admin by username
        Admin admin = adminRepository.findByUsername(username);

        // Check if admin exists and password matches
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        } else {
            return null; // Authentication failed
        }
    }
}
