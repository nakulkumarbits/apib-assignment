package com.bitspilani.fooddeliverysystem.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

class JwtAuthenticationEntryPointTest {

    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();

    @Test
    void testCommence() throws IOException {
        HttpServletResponse expectedResponse = new MockHttpServletResponse();
        jwtAuthenticationEntryPoint.commence(null, expectedResponse, null);
        assertEquals(expectedResponse.getStatus(), HttpServletResponse.SC_UNAUTHORIZED);
    }
}