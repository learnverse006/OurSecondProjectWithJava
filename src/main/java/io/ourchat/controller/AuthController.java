package io.ourchat.controller;

import io.ourchat.dto.LoginRequest;
import io.ourchat.dto.RegisterRequest;
import io.ourchat.model.User;
import io.ourchat.repo.UserRepository;
import io.ourchat.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
//    Đọc body (nội dung JSON) của request
    public ResponseEntity<?> register(@RequestBody RegisterRequest dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email đã tồn tại");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // Sẽ mã hoá ở Ngày 2.5
        user.setFullName(dto.getFullName());

        userRepository.save(user);
        return ResponseEntity.ok("đăng ký thành công");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest dto){
        return userRepository.findByEmail(dto.getEmail())
                .map(user ->{
                    // nếu mat khau khong khop
                    if(!user.getPassword().equals(dto.getPassword())){
                        return ResponseEntity.badRequest().body("Sai mật khẩu");
                    }
                    String token = jwtUtil.generateToken(user.getEmail());
                    return ResponseEntity.ok(
                            Map.of(
                                    "maToken", token,
                                    "email", user.getEmail(),
                                    "hoTen", user.getFullName()
                            )
                    );
                }).orElse(ResponseEntity.badRequest().body("Email không tồn tại"));
    }
}
