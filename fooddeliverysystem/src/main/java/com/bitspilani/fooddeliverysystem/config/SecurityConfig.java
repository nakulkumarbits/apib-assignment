package com.bitspilani.fooddeliverysystem.config;

import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.security.JwtAuthenticationEntryPoint;
import com.bitspilani.fooddeliverysystem.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Following class is used to secure the endpoints of the application
 * and defines which endpoints require what kind of roles to access them.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enable method-level security
public class SecurityConfig {

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/auth/**").permitAll() // Allow access to registration and login endpoints
//                .requestMatchers("/api-docs/**").permitAll() // Allow access to API docs
//                .requestMatchers("/swagger-ui/**").permitAll() // Allow access to Swagger UI
                    .requestMatchers("/swagger-resources/**", "/swagger-resources", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .requestMatchers("/users/customers/**").hasAnyRole(UserRole.CUSTOMER.name(), UserRole.ADMIN.name())
                    .requestMatchers("/users/restaurants/**").hasAnyRole(UserRole.RESTAURANT_OWNER.name(), UserRole.ADMIN.name())
                    .requestMatchers("/menu/**").hasAnyRole(UserRole.RESTAURANT_OWNER.name())
                    .requestMatchers("/users/delivery/**").hasAnyRole(UserRole.DELIVERY_PERSONNEL.name(), UserRole.ADMIN.name())
                    .requestMatchers("/admin/**").hasRole(UserRole.ADMIN.name())
                    .anyRequest().authenticated() // All other requests require authentication
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .csrf(csrf -> csrf.disable()); // Disable CSRF protection for stateless APIs

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
