package com.example.Bank.Service;

import com.example.Bank.Repository.UserRepository;
import com.example.Bank.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) throws Exception{
        if(userRepository.findById(user.getId()).isPresent())
        {
            throw new Exception("User already registered");
        }
        userRepository.save(user);
    }

}
