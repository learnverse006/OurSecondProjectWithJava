package io.ourchat.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component  // Biến class này thành Bean → Spring Boot sẽ tự tạo đối tượng để inject vào controller
public class JwtUtil {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Tạo khóa bí mật để ký token

    public String generateToken(String email) {
        long now = System.currentTimeMillis();
        long oneDay = 24 * 60 * 60 * 1000;
        long expiry = now + oneDay;

        return Jwts.builder()
                .setSubject(email)                  // Gán email vào phần subject của token
                .setIssuedAt(new Date(now))         // Thời điểm tạo
                .setExpiration(new Date(expiry))    // Thời điểm hết hạn
                .signWith(secretKey)                // Ký token bằng khóa bí mật
                .compact();                         // Trả chuỗi token
    }
}
