package com.bitspilani.fooddeliverysystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.dto.DeactivateUserDTO;
import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.dto.RestaurantDTO;
import com.bitspilani.fooddeliverysystem.service.AdministratorService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class AdministratorControllerTest {

    @Mock
    private AdministratorService administratorService;

    @InjectMocks
    private AdministratorController administratorController;

    private List<CustomerDTO> customerDTOList;
    private List<DeliveryPersonnelDTO> deliveryPersonnelDTOList;
    private List<RestaurantDTO> restaurantDTOList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up mock data
        customerDTOList = new ArrayList<>();
        customerDTOList.add(new CustomerDTO());

        deliveryPersonnelDTOList = new ArrayList<>();
        deliveryPersonnelDTOList.add(new DeliveryPersonnelDTO());

        restaurantDTOList = new ArrayList<>();
        restaurantDTOList.add(new RestaurantDTO());
    }

    @Test
    void testGetCustomers_Success() {
        // Mock the service method call
        when(administratorService.getCustomers()).thenReturn(customerDTOList);

        // Call the controller method
        ResponseEntity<List<CustomerDTO>> response = administratorController.getCustomers();

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDTOList, response.getBody());
    }

    @Test
    void testDeactivateUser_Success() {
        DeactivateUserDTO deactivateUserDTO = new DeactivateUserDTO();

        // Mock the service method call
        when(administratorService.deactivateUser(deactivateUserDTO)).thenReturn(deactivateUserDTO);

        // Call the controller method
        ResponseEntity<?> response = administratorController.deactivateUser(deactivateUserDTO);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deactivateUserDTO, response.getBody());
    }

    @Test
    void testGetDeliveryPersonnels_Success() {
        // Mock the service method call
        when(administratorService.getDeliveryPersonnels()).thenReturn(deliveryPersonnelDTOList);

        // Call the controller method
        ResponseEntity<List<DeliveryPersonnelDTO>> response = administratorController.getDeliveryPersonnels();

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deliveryPersonnelDTOList, response.getBody());
    }

    @Test
    void testGetRestaurants_Success() {
        // Mock the service method call
        when(administratorService.getRestaurants()).thenReturn(restaurantDTOList);

        // Call the controller method
        ResponseEntity<List<RestaurantDTO>> response = administratorController.getRestaurants();

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantDTOList, response.getBody());
    }
}
