package com.example.Bank.controller;

import com.example.Bank.Repository.AdminRepository;
import com.example.Bank.Service.AdminService;
import com.example.Bank.dto.AdminLoginRequest;
import com.example.Bank.dto.LoginRequest;
import com.example.Bank.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminRepository adminRepository;
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminRepository adminRepository, AdminService adminService) {
        this.adminRepository = adminRepository;
        this.adminService = adminService;
    }


    @PostMapping("/admin/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AdminLoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Admin admin = adminService.loginAdmin(username, password);

        if (admin != null) {
            return ResponseEntity.ok("Admin authentication successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin authentication failed");
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginAdmin() {
        // Logic to authenticate admin
        // For simplicity, let's just return a success message for now
        return ResponseEntity.ok("Admin login successful");
    }
}
