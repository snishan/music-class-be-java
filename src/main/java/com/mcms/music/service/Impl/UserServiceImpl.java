package com.mcms.music.service.Impl;

import com.mcms.music.dto.LoginRequestDTO;
import com.mcms.music.entity.User;
import com.mcms.music.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    public boolean signUp(LoginRequestDTO loginRequest) {
        String username = loginRequest.getUsername();

        if (userRepository.findByUsername(username) != null) {
            return false; // User already exists
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
        userRepository.save(user);

        return true; // User registered successfully
    }
}
