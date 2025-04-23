package io.ourchat.security;

/**
 * 🔐 Ý nghĩa file:
 * Đây là lớp Filter dùng để chặn các request không có JWT hợp lệ.
 * - Mỗi request đều phải đi qua đây trước khi vào controller.
 * - Nếu có token → kiểm tra hợp lệ → gắn thông tin user vào SecurityContextHolder.
 * - Nếu không có hoặc sai → trả về lỗi 401 Unauthorized.
 * ✅ Dùng cùng với Spring Security thông qua SecurityFilterChain.
 */

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component // Đánh dấu class này là một Spring Bean
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // kiến thức servlet, cần phải học lại
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath(); // Lấy đường dẫn đang được gọi
        // Bỏ qua filter nếu request là login/register
        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Lấy Authorization header
        String authHeader = request.getHeader("Authorization");

        // Kiểm tra xem header có tồn tại và bắt đầu bằng "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // lấy token từ header
            String token = authHeader.substring(7); // Bỏ qua "Bearer "

            // Kiểm tra tính hợp lệ của token
            if (jwtUtil.isTokenValid(token)) {

                String email = jwtUtil.extractEmail(token); // Lấy email từ token

                // Một quyền cụ thể (role) mà user đang có
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));


                //tao đối tượng dai dien xac thực                                                      principal, credentials, authorities
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
                // Gán đối tượng vào SecurityContextHolder -> để nhận ra ai đang gửi request
                SecurityContextHolder.getContext().setAuthentication(authToken);
//                SC_OK (200): Successful response.
//                SC_BAD_REQUEST (400): Bad request.
//                SC_UNAUTHORIZED (401): Unauthorized access.
//                SC_FORBIDDEN (403): Forbidden access.
//                SC_NOT_FOUND (404): Resource not found.
//                SC_INTERNAL_SERVER_ERROR (500): Internal server err
                filterChain.doFilter(request, response);
                return;
            }else {
                //Token sai → chặn lại
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token không hợp lệ hoặc đã hết hạn");
                return;
            }
        }
        // Nếu không có token hoặc không hợp lệ → trả về lỗi 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Bạn chưa đăng nhập hoặc thiếu token");

    }

}
