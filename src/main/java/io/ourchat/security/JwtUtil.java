package io.ourchat.security;

import io.jsonwebtoken.JwtException;
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

    // check token có hop lẹ không
    public boolean isTokenValid(String token){
        try{
            Jwts
                    .parserBuilder() // Khởi tạo trình phân tích token
                    .setSigningKey(secretKey) // Thiết lập khóa bí mật để xác thực token
                    .build() // Xây dựng trình phân tích
                    .parseClaimsJws(token); // Phân tích token và kiểm tra tính hợp lệ
            return true;
        }catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Lấy email từ token
    public String extractEmail(String token){
        return Jwts
                .parserBuilder() // Khởi tạo trình phân tích token
                .setSigningKey(secretKey) // Thiết lập khóa bí mật để xác thực token
                .build() // Xây dựng trình phân tích
                .parseClaimsJws(token) // Phân tích token và kiểm tra tính hợp lệ
                .getBody() // Lấy phần thân của token
                .getSubject(); // Lấy email từ phần subject của token
    }
}
