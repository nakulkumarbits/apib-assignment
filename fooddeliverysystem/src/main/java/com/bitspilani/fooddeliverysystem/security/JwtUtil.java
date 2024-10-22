package com.bitspilani.fooddeliverysystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours
    private final String SECRET_KEY = "IFNN1KagqlbDNeOW4Jys1CrcCp/OapVND+MMF605sng="; // Use a strong secret key

    public String generateToken(String username, List<String> roles, Long ownerId, Long customerId,
        Long deliveryPersonnelId) {
        Map<String, Object> claims = new HashMap<>();
        if (ownerId != null) {
            claims.put("ownerId", ownerId);
        }
        if (customerId != null) {
            claims.put("customerId", customerId);
        }
        if (deliveryPersonnelId != null) {
            claims.put("deliveryPersonnelId", deliveryPersonnelId);
        }
        claims.put("roles", roles);
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public Long extractOwnerIdFromToken(String token) {
        Claims claims = extractAllClaims(token.substring(7));
        return ((Number) claims.get("ownerId")).longValue(); // Cast to Long
    }

    public Long extractCustomerIdFromToken(String token) {
        Claims claims = extractAllClaims(token.substring(7));
        return ((Number) claims.get("customerId")).longValue();
    }

    public Long extractDeliveryPersonnelIdFromToken(String token) {
        Claims claims = extractAllClaims(token.substring(7));
        return getClaimByKey(claims, "deliveryPersonnelId");
    }

    private Long getClaimByKey(Claims claims, String key) {
        return ((Number) claims.get(key)).longValue();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
