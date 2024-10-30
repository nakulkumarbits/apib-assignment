package com.bitspilani.fooddeliverysystem.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractFoodDeliveryTest {

    private static final int EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours
    private static final String SECRET_KEY = "WoDLBwKrdRF7VHkZsHNgGNFPnq8wCc4n4nu+X8+G3Qs=";

    public static String createTokenForRole(String role, String subject) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of(role));
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

}
