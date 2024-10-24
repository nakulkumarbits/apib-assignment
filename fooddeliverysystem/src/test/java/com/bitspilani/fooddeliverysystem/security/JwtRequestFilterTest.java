package com.bitspilani.fooddeliverysystem.security;

import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.API_SECURE_ENDPOINT;
import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.AUTHORIZATION;
import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.AUTH_LOGIN;
import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.BEARER_BLACKLISTED_JWT_TOKEN;
import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.BEARER_INVALID_JWT_TOKEN;
import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.BEARER_VALID_JWT_TOKEN;
import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.BLACKLISTED_JWT_TOKEN;
import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.INVALID_JWT_TOKEN;
import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.USERNAME;
import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.VALID_JWT_TOKEN;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.repository.BlacklistedTokenRepository;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class JwtRequestFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService customUserDetailsService;

    @Mock
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterInternal_ValidJwtToken() throws ServletException, IOException {
        // Mock the Authorization header
        when(request.getRequestURI()).thenReturn(API_SECURE_ENDPOINT);
        when(request.getHeader(AUTHORIZATION)).thenReturn(BEARER_VALID_JWT_TOKEN);
        when(jwtUtil.extractUsername(VALID_JWT_TOKEN)).thenReturn(USERNAME);
        when(jwtUtil.validateToken(VALID_JWT_TOKEN, USERNAME)).thenReturn(true);
        when(blacklistedTokenRepository.existsByToken(VALID_JWT_TOKEN)).thenReturn(false);

        // Mock UserDetails
        when(customUserDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(null);

        // Simulate JWT validation
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verify that the authentication token is set correctly in the security context
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
            null, null);
        verify(jwtUtil, times(1)).validateToken(VALID_JWT_TOKEN, USERNAME);
        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_BlacklistedJwtToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn(API_SECURE_ENDPOINT);
        when(request.getHeader(AUTHORIZATION)).thenReturn(BEARER_BLACKLISTED_JWT_TOKEN);
        when(jwtUtil.extractUsername(BLACKLISTED_JWT_TOKEN)).thenReturn(USERNAME);
        when(blacklistedTokenRepository.existsByToken(BLACKLISTED_JWT_TOKEN)).thenReturn(true);

        // Simulate blacklisted token scenario
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verify that response sends error for blacklisted token
        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, FoodDeliveryConstants.TOKEN_LOGOUT_MSG);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_NoAuthorizationHeader() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn(API_SECURE_ENDPOINT);
        when(request.getHeader(AUTHORIZATION)).thenReturn(null);

        // Simulate request with no Authorization header
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verify that filter chain proceeds without setting authentication
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_NoBearerInHeader() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn(API_SECURE_ENDPOINT);
        when(request.getHeader(AUTHORIZATION)).thenReturn(VALID_JWT_TOKEN);

        // Simulate request with no Authorization header
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verify that filter chain proceeds without setting authentication
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_AuthEndpoint_SkipsJwtValidation_DirectInvocation()
        throws ServletException, IOException {
        // Mock request to an auth endpoint
        when(request.getRequestURI()).thenReturn(AUTH_LOGIN);

        // Invoke doFilterInternal directly
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verify that the filter chain proceeds without checking for JWT
        verify(jwtUtil, never()).extractUsername(anyString());  // JWT extraction should not be invoked
        verify(jwtUtil, never()).validateToken(anyString(), anyString());  // Token validation should not be invoked
        verify(blacklistedTokenRepository, never()).existsByToken(
            anyString());  // Blacklist check should not be invoked
        verify(filterChain, times(1)).doFilter(request, response);  // The filter chain should proceed
    }

    @Test
    void testDoFilterInternal_InvalidJwtToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn(API_SECURE_ENDPOINT);
        when(request.getHeader(AUTHORIZATION)).thenReturn(BEARER_INVALID_JWT_TOKEN);
        when(jwtUtil.extractUsername(INVALID_JWT_TOKEN)).thenReturn(USERNAME);
        when(jwtUtil.validateToken(INVALID_JWT_TOKEN, USERNAME)).thenReturn(false);

        // Simulate invalid token scenario
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verify that the filter chain proceeds and no authentication is set
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
