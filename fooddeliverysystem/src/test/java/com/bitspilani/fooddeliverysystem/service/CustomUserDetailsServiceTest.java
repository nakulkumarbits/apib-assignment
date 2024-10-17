package com.bitspilani.fooddeliverysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class CustomUserDetailsServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    static Stream<Object[]> userRoleProvider() {
        return Stream.of(
            new Object[]{UserRole.ADMIN, FoodDeliveryConstants.ROLE_ADMIN},
            new Object[]{UserRole.CUSTOMER, FoodDeliveryConstants.ROLE_CUSTOMER},
            new Object[]{UserRole.RESTAURANT_OWNER, FoodDeliveryConstants.ROLE_RESTAURANT_OWNER},
            new Object[]{UserRole.DELIVERY_PERSONNEL, FoodDeliveryConstants.ROLE_DELIVERY_PERSONNEL}
        );
    }

    @ParameterizedTest
    @MethodSource("userRoleProvider")
    void testLoadUserByUsername(UserRole userRole, String role) {
        when(userRepository.findByUsername(anyString())).thenReturn(getUser(userRole));
        UserDetails result = customUserDetailsService.loadUserByUsername("username");
        GrantedAuthority grantedAuthority = result.getAuthorities().stream().findFirst().get();
        Assertions.assertEquals(role, grantedAuthority.getAuthority());
    }

    static Stream<Object[]> invalidUserProvider() {
        return Stream.of(
            new Object[]{null},
            new Object[]{new User()}
            );
    }

    @ParameterizedTest
    @MethodSource("invalidUserProvider")
    void testUserNotFound(User user) {
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
            () -> customUserDetailsService.loadUserByUsername("username"));
        assertEquals(FoodDeliveryConstants.USER_NOT_PRESENT, exception.getMessage());
    }

    private User getUser(UserRole userRole) {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(userRole);
        return user;
    }
}