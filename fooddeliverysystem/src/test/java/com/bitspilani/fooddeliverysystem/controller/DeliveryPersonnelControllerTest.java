package com.bitspilani.fooddeliverysystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.service.DeliveryPersonnelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class DeliveryPersonnelControllerTest {

    @Mock
    private DeliveryPersonnelService deliveryPersonnelService;

    @InjectMocks
    private DeliveryPersonnelController deliveryPersonnelController;

    private DeliveryPersonnelDTO deliveryPersonnelDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a sample DeliveryPersonnelDTO
        deliveryPersonnelDTO = new DeliveryPersonnelDTO();
        deliveryPersonnelDTO.setUsername("john_doe");
    }

    @Test
    void testGetDeliveryPersonnel_Success() {
        String username = "john_doe";

        // Mock the service method call
        when(deliveryPersonnelService.getDeliveryPersonnel(username)).thenReturn(deliveryPersonnelDTO);

        // Call the controller method
        ResponseEntity<DeliveryPersonnelDTO> response = deliveryPersonnelController.getDeliveryPersonnel(username);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deliveryPersonnelDTO, response.getBody());
    }

    @Test
    void testUpdateDeliveryPersonnel_Success() {
        String username = "john_doe";

        // Mock the service method call
        when(deliveryPersonnelService.updateDeliveryPersonnel(deliveryPersonnelDTO, username)).thenReturn(
            deliveryPersonnelDTO);

        // Call the controller method
        ResponseEntity<DeliveryPersonnelDTO> response = deliveryPersonnelController.updateDeliveryPersonnel(username,
            deliveryPersonnelDTO);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deliveryPersonnelDTO, response.getBody());
    }

    @Test
    void testGetDeliveryPersonnel_NotFound() {
        String username = "unknown_user";

        // Mock the service method call to return null or throw an exception for a non-existing user
        when(deliveryPersonnelService.getDeliveryPersonnel(username)).thenThrow(new RuntimeException("User not found"));

        // Call the controller method
        try {
            deliveryPersonnelController.getDeliveryPersonnel(username);
        } catch (RuntimeException e) {
            assertEquals("User not found", e.getMessage());
        }
    }

    @Test
    void testUpdateDeliveryPersonnel_BadRequest() {
        String username = "john_doe";

        // Mock the service method call to throw an exception
        when(deliveryPersonnelService.updateDeliveryPersonnel(deliveryPersonnelDTO, username)).thenThrow(
            new RuntimeException("Invalid input"));

        // Call the controller method
        try {
            deliveryPersonnelController.updateDeliveryPersonnel(username, deliveryPersonnelDTO);
        } catch (RuntimeException e) {
            assertEquals("Invalid input", e.getMessage());
        }
    }
}
