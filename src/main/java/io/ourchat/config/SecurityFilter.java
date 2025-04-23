//Cấu hình để mở các API công khai như /register, /login, còn các API khác phải có token mới truy cập được.
package io.ourchat.config;
import io.ourchat.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityFilter {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter ) throws Exception{
        // https://viettelidc.com.vn/tin-tuc/csrf-la-gi-tim-hieu-giai-phap-phong-chong-hieu-qua#:~:text=CSRF%20(vi%E1%BA%BFt%20t%E1%BA%AFt%20c%E1%BB%A7a%20Cross,d%C3%B9ng%20%C4%91%C3%A3%20%C4%91%C6%B0%E1%BB%A3c%20x%C3%A1c%20th%E1%BB%B1c.
        http
            //Tắt CSRF vì mình đang làm API thuần, không phải web form
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Cho phép gọi /register, /login không cần token
                        .anyRequest().authenticated()               // Những API còn lại phải có token
                ).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);// Thêm filter kiểm tra token vào trước khi xác thực
        return http.build();

    }
}
