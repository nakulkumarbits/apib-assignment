package com.bitspilani.fooddeliverysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants;
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
        UserDetails result = customUserDetailsService.loadUserByUsername(FoodDeliveryTestConstants.USERNAME);
        GrantedAuthority grantedAuthority = result.getAuthorities().stream().findFirst().get();
        Assertions.assertEquals(role, grantedAuthority.getAuthority());
    }

    @Test
    void testUserNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
            () -> customUserDetailsService.loadUserByUsername(FoodDeliveryTestConstants.USERNAME));
        assertEquals(FoodDeliveryConstants.USER_NOT_PRESENT, exception.getMessage());
    }

    @Test
    void testUpdateLastLogin() {
        when(userRepository.findByUsername(FoodDeliveryTestConstants.USERNAME)).thenReturn(getUser(UserRole.CUSTOMER));
        customUserDetailsService.updateLastLogin(FoodDeliveryTestConstants.USERNAME);
        verify(userRepository, times(1)).save(any());
    }

    private User getUser(UserRole userRole) {
        User user = new User();
        user.setUsername(FoodDeliveryTestConstants.USERNAME);
        user.setPassword("password");
        user.setRole(userRole);
        return user;
    }
}