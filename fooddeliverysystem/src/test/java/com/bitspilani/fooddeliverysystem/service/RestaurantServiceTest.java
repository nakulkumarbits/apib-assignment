package com.bitspilani.fooddeliverysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.RestaurantDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.UsernameMismatchException;
import com.bitspilani.fooddeliverysystem.model.Restaurant;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.RestaurantRepository;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class RestaurantServiceTest {

    @Mock
    RestaurantRepository restaurantRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodeResponse");
    }

    @Test
    void testGetRestaurant() {
        when(restaurantRepository.findByUser(any(User.class))).thenReturn(getRestaurant());
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(new User());

        RestaurantDTO result = restaurantService.getRestaurant("username");
        Assertions.assertEquals(new RestaurantDTO(), result);
    }

    @Test
    void testGetRestaurantWhenUserNotFound() {
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(null);
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> restaurantService.getRestaurant("username"));
        assertEquals(FoodDeliveryConstants.RESTAURANT_NOT_PRESENT, exception.getMessage());
        verify(userRepository, times(1)).findByUsernameAndRole(anyString(), any());
    }

    @Test
    void testGetRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(List.of(getRestaurant()));
        List<RestaurantDTO> result = restaurantService.getRestaurants();
        Assertions.assertEquals(List.of(new RestaurantDTO()), result);
    }

    @Test
    void testUpdateRestaurant() {
        when(restaurantRepository.findByUser(any(User.class))).thenReturn(getRestaurant());
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(new User());
        when(restaurantRepository.save(any())).thenReturn(getRestaurant());

        RestaurantDTO result = restaurantService.updateRestaurant(getRestaurantDTO(), "username");
        Assertions.assertEquals(new RestaurantDTO(), result);
    }

    @Test
    void testUpdateRestaurantUsernameMismatch() {
        UsernameMismatchException exception = assertThrows(UsernameMismatchException.class,
            () -> restaurantService.updateRestaurant(new RestaurantDTO(), "username"));
        assertEquals(FoodDeliveryConstants.USERNAME_MISMATCH, exception.getMessage());
    }

    @Test
    void testUpdateRestaurantWhenUserNotFound() {
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(null);
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
            () -> restaurantService.updateRestaurant(getRestaurantDTO(), "username"));
        assertEquals(FoodDeliveryConstants.RESTAURANT_NOT_PRESENT, exception.getMessage());
    }

    @Test
    void testGetRestaurantByUsername() {
        when(userRepository.findByUsernameAndRole(anyString(), any())).thenReturn(new User());
        when(restaurantRepository.findByUser(any())).thenReturn(getRestaurant());
        Restaurant restaurant = restaurantService.getRestaurantByUsername("username");
        assertNotNull(restaurant);
    }

    @Test
    void testGetRestaurantByUsernameCanReturnNull() {
        when(userRepository.findByUsernameAndRole(anyString(), any())).thenReturn(null);
        Restaurant restaurant = restaurantService.getRestaurantByUsername("username");
        assertNull(restaurant);
    }

    @Test
    void testGetRestaurantById() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.of(new Restaurant()));
        Restaurant restaurant = restaurantService.getRestaurantById(10L);
        assertNotNull(restaurant);
    }

    @Test
    void testGetRestaurantByIdCanReturnNull() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());
        Restaurant restaurant = restaurantService.getRestaurantById(10L);
        assertNull(restaurant);
    }

    private Restaurant getRestaurant() {
        Restaurant owner = new Restaurant();
        owner.setUser(new User());
        return owner;
    }

    private RestaurantDTO getRestaurantDTO() {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setUsername("username");
        return dto;
    }
}