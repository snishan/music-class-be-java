package com.mcms.music.controller;

import com.mcms.music.dto.LoginRequestDTO;
import com.mcms.music.repository.UserRepository;
import com.mcms.music.service.Impl.UserServiceImpl;
import com.mcms.music.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/music-api/v1")
@CrossOrigin
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<StandardResponse> login(@RequestBody LoginRequestDTO loginRequest) {
        boolean isAuthenticated = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok(new StandardResponse(200, "Login successful", isAuthenticated));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(401, "Invalid credentials", isAuthenticated));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<StandardResponse> signUp(@RequestBody LoginRequestDTO loginRequest) {
        boolean isRegistered = userService.signUp(loginRequest);
        if (isRegistered) {
            return ResponseEntity.ok(new StandardResponse(200, "User registered successfully", true));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new StandardResponse(401, "Username already exists", false));
        }
    }
}
