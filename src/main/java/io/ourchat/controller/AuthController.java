package io.ourchat.controller;

import io.ourchat.dto.LoginRequest;
import io.ourchat.dto.RegisterRequest;
import io.ourchat.model.entity.User;
import io.ourchat.repo.UserRepository;
import io.ourchat.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController // ý nghĩa tương tự @Controller (web controller: nhận và xử lí từ client/web) + @ResponseBody (trả về JSON)
@RequestMapping("/api/auth")

public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;// Spring sẽ tự động đưa PasswordEncoder vào đây từ SecurityConfig

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
//    Đọc body (nội dung JSON) của request
    public ResponseEntity<?> register(@RequestBody RegisterRequest dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email đã tồn tại");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
//        user.setPassword(dto.getPassword()); // Sẽ mã hoá ở Ngày 2.5
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());

        userRepository.save(user);
        return ResponseEntity.ok("đăng ký thành công");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest dto){
        return userRepository.findByEmail(dto.getEmail())
                .map(user ->{
                    // nếu mat khau khong khop
//                    if(!user.getPassword().equals(dto.getPassword())){
//                        return ResponseEntity.badRequest().body("Sai mật khẩu");
//                    }
                    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
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
    // Để kiểm tra xem token có hợp lệ không
    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok("Email hiện tại: " + email);
    }


}
