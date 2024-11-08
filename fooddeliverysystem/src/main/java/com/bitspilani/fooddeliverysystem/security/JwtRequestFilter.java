package com.bitspilani.fooddeliverysystem.security;

import com.bitspilani.fooddeliverysystem.repository.BlacklistedTokenRepository;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        final String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/auth/")) {
            chain.doFilter(request, response); // Skip JWT validation for auth endpoints
            return;
        }

        final String authorizationHeader = request.getHeader(FoodDeliveryConstants.AUTHORIZATION);

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(FoodDeliveryConstants.BEARER)) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);

            // Check if the token is blacklisted
            if (blacklistedTokenRepository.existsByToken(jwt)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, FoodDeliveryConstants.TOKEN_LOGOUT_MSG);
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, username)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
