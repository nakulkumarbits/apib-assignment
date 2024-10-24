package com.bitspilani.fooddeliverysystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.RestaurantDTO;
import com.bitspilani.fooddeliverysystem.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class RestaurantOwnerControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantOwnerController restaurantOwnerController;

    private RestaurantDTO restaurantDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a sample RestaurantDTO
        restaurantDTO = new RestaurantDTO();
        restaurantDTO.setUsername("pizza_place");
    }

    @Test
    void testGetRestaurant_Success() {
        String username = "pizza_place";

        // Mock the service method call
        when(restaurantService.getRestaurant(username)).thenReturn(restaurantDTO);

        // Call the controller method
        ResponseEntity<RestaurantDTO> response = restaurantOwnerController.getRestaurant(username);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantDTO, response.getBody());
    }

    @Test
    void testUpdateRestaurant_Success() {
        String username = "pizza_place";

        // Mock the service method call
        when(restaurantService.updateRestaurant(restaurantDTO, username)).thenReturn(restaurantDTO);

        // Call the controller method
        ResponseEntity<RestaurantDTO> response = restaurantOwnerController.updateRestaurant(username, restaurantDTO);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantDTO, response.getBody());
    }
}
