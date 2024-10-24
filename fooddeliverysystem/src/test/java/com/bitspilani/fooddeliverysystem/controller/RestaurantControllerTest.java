package com.bitspilani.fooddeliverysystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.RestaurantItemDTO;
import com.bitspilani.fooddeliverysystem.service.RestaurantService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    private List<RestaurantItemDTO> restaurantItemDTOList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up mock data
        restaurantItemDTOList = new ArrayList<>();
        restaurantItemDTOList.add(new RestaurantItemDTO());
        restaurantItemDTOList.add(new RestaurantItemDTO());
    }

    @Test
    void testGetAllRestaurants_Success() {
        // Mock the service method call
        when(restaurantService.getRestaurantsForCustomers(null, null, null)).thenReturn(restaurantItemDTOList);

        // Call the controller method
        ResponseEntity<List<RestaurantItemDTO>> response = restaurantController.getAllRestaurants(null, null, null);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantItemDTOList, response.getBody());
    }

    @Test
    void testGetAllRestaurants_WithCuisineType() {
        // Mock the service method call
        String cuisineType = "Italian";
        when(restaurantService.getRestaurantsForCustomers(cuisineType, null, null)).thenReturn(restaurantItemDTOList);

        // Call the controller method
        ResponseEntity<List<RestaurantItemDTO>> response = restaurantController.getAllRestaurants(cuisineType, null,
            null);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantItemDTOList, response.getBody());
    }

    @Test
    void testGetAllRestaurants_WithItemType() {
        // Mock the service method call
        String itemType = "MAIN_COURSE";
        when(restaurantService.getRestaurantsForCustomers(null, itemType, null)).thenReturn(restaurantItemDTOList);

        // Call the controller method
        ResponseEntity<List<RestaurantItemDTO>> response = restaurantController.getAllRestaurants(null, itemType, null);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantItemDTOList, response.getBody());
    }

    @Test
    void testGetAllRestaurants_WithItemAvailable() {
        // Mock the service method call
        String itemAvailable = "YES";
        when(restaurantService.getRestaurantsForCustomers(null, null, itemAvailable)).thenReturn(restaurantItemDTOList);

        // Call the controller method
        ResponseEntity<List<RestaurantItemDTO>> response = restaurantController.getAllRestaurants(null, null,
            itemAvailable);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantItemDTOList, response.getBody());
    }
}
