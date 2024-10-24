package com.bitspilani.fooddeliverysystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a sample CustomerDTO
        customerDTO = new CustomerDTO();
        customerDTO.setUsername("john_doe");
        customerDTO.setEmail("john@example.com");
    }

    @Test
    void testGetCustomer_Success() {
        String username = "john_doe";

        // Mock the service method call
        when(customerService.getCustomer(username)).thenReturn(customerDTO);

        // Call the controller method
        ResponseEntity<CustomerDTO> response = customerController.getCustomer(username);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDTO, response.getBody());
    }

    @Test
    void testUpdateCustomer_Success() {
        String username = "john_doe";

        // Mock the service method call
        when(customerService.updateCustomer(customerDTO, username)).thenReturn(customerDTO);

        // Call the controller method
        ResponseEntity<CustomerDTO> response = customerController.updateCustomer(username, customerDTO);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDTO, response.getBody());
    }

    @Test
    void testGetCustomer_NotFound() {
        String username = "unknown_user";

        // Mock the service method call to throw an exception for a non-existing user
        when(customerService.getCustomer(username)).thenThrow(new RuntimeException("User not found"));

        // Call the controller method
        try {
            customerController.getCustomer(username);
        } catch (RuntimeException e) {
            assertEquals("User not found", e.getMessage());
        }
    }

    @Test
    void testUpdateCustomer_BadRequest() {
        String username = "john_doe";
        customerDTO.setEmail(null); // Simulate invalid input (e.g., missing required field)

        // Mock the service method call to throw an exception
        when(customerService.updateCustomer(customerDTO, username)).thenThrow(new RuntimeException("Invalid input"));

        // Call the controller method
        try {
            customerController.updateCustomer(username, customerDTO);
        } catch (RuntimeException e) {
            assertEquals("Invalid input", e.getMessage());
        }
    }
}
