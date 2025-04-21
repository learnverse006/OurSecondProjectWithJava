package io.ourchat.controller;

import io.ourchat.dto.RegisterRequest;
import io.ourchat.model.User;
import io.ourchat.repo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // Sẽ mã hoá ở Ngày 2.5
        user.setFullName(dto.getFullName());

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
