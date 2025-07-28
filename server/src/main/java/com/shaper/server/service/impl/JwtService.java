package com.shaper.server.service.impl;

import com.shaper.server.model.entity.User;
import com.shaper.server.model.entity.HrUser;
import com.shaper.server.model.entity.Hire;
import com.shaper.server.security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final JwtUtil jwtUtil;

    public JwtService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String generateToken(User user) {
        String role;
        if (user instanceof HrUser) {
            role = "HR";
        } else if (user instanceof Hire) {
            role = "HIRE";
        } else {
            throw new IllegalArgumentException("Unknown user type");
        }
        return jwtUtil.generateToken(user.getEmail(), role);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = jwtUtil.extractUsername(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return jwtUtil.extractClaims(token).getExpiration().before(new Date());
    }
}
