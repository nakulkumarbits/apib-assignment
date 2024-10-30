package com.bitspilani.fooddeliverysystem.security;

import static com.bitspilani.fooddeliverysystem.security.JwtUtil.EXPIRATION_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class JwtUtilTest {

    JwtUtil jwtUtil = new JwtUtil();

    static Stream<Object[]> idProvider() {
        return Stream.of(
            new Object[]{1L, 1L, 1L},
            new Object[]{null, null, null}
        );
    }

    @ParameterizedTest
    @MethodSource("idProvider")
    void testGenerateToken(Long ownerId, Long customerId, Long personnelId) {
        String result = jwtUtil.generateToken(FoodDeliveryTestConstants.USERNAME, List.of("roles"), ownerId, customerId,
            personnelId);
        assertNotNull(result);
    }

    @Test
    void testHasAdminRole_ValidTokenWithAdminRole() throws NoSuchFieldException, IllegalAccessException {

        Field secretKeyField = JwtUtil.class.getDeclaredField("SECRET_KEY");
        secretKeyField.setAccessible(true);

        // Create a mock JWT builder with admin role
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", Collections.singletonList(FoodDeliveryConstants.ROLE_ADMIN));
        String token = Jwts.builder()
            .setClaims(claims)
            .setSubject("")
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, secretKeyField.get("SECRET_KEY").toString())
            .compact();

        boolean isAdmin = jwtUtil.hasAdminRole("Bearer " + token);
        assertTrue(isAdmin);
    }

    @Test
    void testExtractUsername() {
        String token = jwtUtil.generateToken(FoodDeliveryTestConstants.USERNAME, List.of("roles"), 1L, null, null);
        String result = jwtUtil.extractUsername(token);
        assertEquals(FoodDeliveryTestConstants.USERNAME, result);
    }

    @Test
    void testValidateToken() {
        String token = jwtUtil.generateToken(FoodDeliveryTestConstants.USERNAME, List.of("roles"), 1L, null, null);
        boolean result = jwtUtil.validateToken(token, FoodDeliveryTestConstants.USERNAME);
        assertTrue(result);
    }

    @Test
    void testExtractOwnerIdFromToken() {
        String token = jwtUtil.generateToken(FoodDeliveryTestConstants.USERNAME, List.of("roles"), 1L, null, null);
        Long result = jwtUtil.extractOwnerIdFromToken("Bearer " + token);
        assertEquals(Long.valueOf(1), result);
    }

    @Test
    void testExtractCustomerIdFromToken() {
        String token = jwtUtil.generateToken(FoodDeliveryTestConstants.USERNAME, List.of("roles"), null, 1L, null);
        Long result = jwtUtil.extractCustomerIdFromToken("Bearer " + token);
        assertEquals(Long.valueOf(1), result);
    }

    @Test
    void testExtractDeliveryPersonnelIdFromToken() {
        String token = jwtUtil.generateToken(FoodDeliveryTestConstants.USERNAME, List.of("roles"), null, null, 1L);
        Long result = jwtUtil.extractDeliveryPersonnelIdFromToken("Bearer " + token);
        assertEquals(Long.valueOf(1), result);
    }
}