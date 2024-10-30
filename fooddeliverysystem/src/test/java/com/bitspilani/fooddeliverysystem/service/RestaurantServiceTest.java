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
import com.bitspilani.fooddeliverysystem.dto.RestaurantItemDTO;
import com.bitspilani.fooddeliverysystem.enums.CuisineType;
import com.bitspilani.fooddeliverysystem.enums.ItemAvailable;
import com.bitspilani.fooddeliverysystem.enums.ItemType;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.UsernameMismatchException;
import com.bitspilani.fooddeliverysystem.model.MenuItem;
import com.bitspilani.fooddeliverysystem.model.Restaurant;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.MenuItemRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantDeliveryZoneRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantOpeningDetailRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantRepository;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

class RestaurantServiceTest {

    @Mock
    RestaurantRepository restaurantRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    MenuItemRepository menuItemRepository;
    @Mock
    RestaurantOpeningDetailRepository restaurantOpeningDetailRepository;
    @Mock
    RestaurantDeliveryZoneRepository restaurantDeliveryZoneRepository;
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
        assertNotNull(result);
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
        assertNotNull(result);
    }

    @Test
    void testUpdateRestaurant() {
        when(restaurantRepository.findByUser(any(User.class))).thenReturn(getRestaurant());
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(new User());
        when(restaurantRepository.save(any())).thenReturn(getRestaurant());

        RestaurantDTO result = restaurantService.updateRestaurant(getRestaurantDTO(), "username");
        assertNotNull(result);
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

    static Stream<Object[]> paramProvider() {
        return Stream.of(
            new Object[]{null, null, null},
            new Object[]{CuisineType.INDIAN.name(), ItemType.VEG.name(), ItemAvailable.YES.name()}
        );
    }

    @ParameterizedTest
    @MethodSource("paramProvider")
    void testGetRestaurantsForCustomers(String cuisine, String type, String availability) {
        MenuItem menuItem = new MenuItem();
        menuItem.setRestaurant(getRestaurant());
        when(menuItemRepository.findAll(any(Specification.class))).thenReturn(List.of(menuItem));
        List<RestaurantItemDTO> restaurantItemDTOS = restaurantService.getRestaurantsForCustomers(cuisine, type, availability);
        assertNotNull(restaurantItemDTOS);
    }

    private Restaurant getRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(RandomStringUtils.randomAlphabetic(10));
        restaurant.setUser(new User());
        return restaurant;
    }

    private RestaurantDTO getRestaurantDTO() {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setUsername("username");
        return dto;
    }
}