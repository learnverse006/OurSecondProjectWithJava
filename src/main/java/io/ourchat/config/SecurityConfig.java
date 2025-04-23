package io.ourchat.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration

public class SecurityConfig {

    @Bean // tạo một Bean (object do Spring quản lý) để dùng lại ở mọi nơi
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
