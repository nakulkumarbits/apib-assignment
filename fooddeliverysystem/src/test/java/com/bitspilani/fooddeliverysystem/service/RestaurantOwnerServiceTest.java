package com.bitspilani.fooddeliverysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.dto.RestaurantOwnerDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.UsernameMismatchException;
import com.bitspilani.fooddeliverysystem.model.RestaurantOwner;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.RestaurantOwnerRepository;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class RestaurantOwnerServiceTest {

    @Mock
    RestaurantOwnerRepository restaurantOwnerRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    RestaurantOwnerService restaurantOwnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodeResponse");
    }

    @Test
    void testGetRestaurantOwner() {
        when(restaurantOwnerRepository.findByUser(any(User.class))).thenReturn(getRestaurantOwner());
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(new User());

        RestaurantOwnerDTO result = restaurantOwnerService.getRestaurantOwner("username");
        Assertions.assertEquals(new RestaurantOwnerDTO(), result);
    }

    @Test
    void testGetRestaurantOwnerWhenUserNotFound() {
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(null);
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> restaurantOwnerService.getRestaurantOwner("username"));
        assertEquals(FoodDeliveryConstants.RESTAURANT_NOT_PRESENT, exception.getMessage());
        verify(userRepository, times(1)).findByUsernameAndRole(anyString(), any());
    }

    @Test
    void testGetRestaurantOwners() {
        when(restaurantOwnerRepository.findAll()).thenReturn(List.of(getRestaurantOwner()));
        List<RestaurantOwnerDTO> result = restaurantOwnerService.getRestaurantOwners();
        Assertions.assertEquals(List.of(new RestaurantOwnerDTO()), result);
    }

    @Test
    void testUpdateRestaurantOwner() {
        when(restaurantOwnerRepository.findByUser(any(User.class))).thenReturn(getRestaurantOwner());
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(new User());
        when(restaurantOwnerRepository.save(any())).thenReturn(getRestaurantOwner());

        RestaurantOwnerDTO result = restaurantOwnerService.updateRestaurantOwner(getRestaurantOwnerDTO(), "username");
        Assertions.assertEquals(new RestaurantOwnerDTO(), result);
    }

    @Test
    void testUpdateRestaurantOwnerUsernameMismatch() {
        UsernameMismatchException exception = assertThrows(UsernameMismatchException.class,
            () -> restaurantOwnerService.updateRestaurantOwner(new RestaurantOwnerDTO(), "username"));
        assertEquals(FoodDeliveryConstants.USERNAME_MISMATCH, exception.getMessage());
    }

    @Test
    void testUpdateRestaurantOwnerWhenUserNotFound() {
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(null);
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
            () -> restaurantOwnerService.updateRestaurantOwner(getRestaurantOwnerDTO(), "username"));
        assertEquals(FoodDeliveryConstants.RESTAURANT_NOT_PRESENT, exception.getMessage());
    }

    private RestaurantOwner getRestaurantOwner() {
        RestaurantOwner owner = new RestaurantOwner();
        owner.setUser(new User());
        return owner;
    }

    private RestaurantOwnerDTO getRestaurantOwnerDTO() {
        RestaurantOwnerDTO dto = new RestaurantOwnerDTO();
        dto.setUsername("username");
        return dto;
    }
}