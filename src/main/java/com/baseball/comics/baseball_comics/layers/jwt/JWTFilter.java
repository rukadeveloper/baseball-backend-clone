package com.baseball.comics.baseball_comics.layers.jwt;

import com.baseball.comics.baseball_comics.layers.dto.userDetails.CustomUserDetails;
import com.baseball.comics.baseball_comics.layers.repository.User.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Qualifier("jwtUtils")
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String path = request.getRequestURI();

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        if(path.equals("/find/id") || path.equals("/upload")) {
            filterChain.doFilter(request, response);
            return;
        }

        if(jwtUtil.isExpired(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String username = jwtUtil.getUsername(token);
        String encodedPassword = jwtUtil.getPassword(token);
        String role = jwtUtil.getRole(token);
        String uname = jwtUtil.getName(token);
        String uemail = jwtUtil.getEmail(token);
        String phone = jwtUtil.getPhone(token);
        String profile = jwtUtil.getProfile(token);

        UserEntity userEntity = new UserEntity();
        userEntity.setUid(username);
        userEntity.setPassword(encodedPassword);
        userEntity.setRole(role);
        userEntity.setUname(uname);
        userEntity.setUemail(uemail);
        userEntity.setUphone(phone);
        userEntity.setProfile(profile);

        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request,response);
    }
}
