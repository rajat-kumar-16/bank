package com.example.Bank.Service;

import com.example.Bank.Repository.UserRepository;
import com.example.Bank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {

        User savedUser = userRepository.save(user);
        userRepository.save(savedUser);
        return savedUser;
    }
}
