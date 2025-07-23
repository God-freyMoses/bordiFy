package com.shaper.server.service.impl;

import com.shaper.server.model.entity.Hire;
import com.shaper.server.model.entity.HrUser;
import com.shaper.server.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "bZNatHk6UxEnAtz9TfNKGAobhCZbCvaFEs58ZeG3GNhCOvVhlDE0ST7WtQculYJJ";
    private final long EXPIRATION_TIME = 86400000; // 1 day

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        
        // Add user type to claims
        if (user instanceof HrUser) {
            claims.put("userType", "HR");
        } else if (user instanceof Hire) {
            claims.put("userType", "HIRE");
        }
        
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(user.getEmail())
                .issuer("shaper")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .and()
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public String extractUserType(String token) {
        return extractClaims(token).get("userType", String.class);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractUserName(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
