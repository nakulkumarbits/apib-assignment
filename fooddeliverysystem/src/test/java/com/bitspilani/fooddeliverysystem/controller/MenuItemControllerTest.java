package com.bitspilani.fooddeliverysystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.MenuItemDTO;
import com.bitspilani.fooddeliverysystem.service.MenuItemService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class MenuItemControllerTest {

    @Mock
    private MenuItemService menuItemService;

    @InjectMocks
    private MenuItemController menuItemController;

    private MenuItemDTO menuItemDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a sample MenuItemDTO
        menuItemDTO = new MenuItemDTO();
        menuItemDTO.setName("Pizza");
        menuItemDTO.setDescription("Delicious cheese pizza");
        menuItemDTO.setPrice(10.99);
        // Add other necessary fields as needed
    }

    @Test
    void testAddMenuItem_Success() {
        String token = "Bearer sampleToken";

        // Mock the service method call
        when(menuItemService.addMenuItem(menuItemDTO, token)).thenReturn(menuItemDTO);

        // Call the controller method
        ResponseEntity<MenuItemDTO> response = menuItemController.addMenuItem(menuItemDTO, token);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(menuItemDTO, response.getBody());
    }

    @Test
    void testUpdateMenuItem_Success() {
        String token = "Bearer sampleToken";
        Long menuItemId = 1L;

        // Mock the service method call
        when(menuItemService.updateMenuItem(menuItemId, menuItemDTO, token)).thenReturn(menuItemDTO);

        // Call the controller method
        ResponseEntity<MenuItemDTO> response = menuItemController.updateMenuItem(menuItemId, menuItemDTO, token);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(menuItemDTO, response.getBody());
    }

    @Test
    void testRemoveMenuItem_Success() {
        String token = "Bearer sampleToken";
        Long menuItemId = 1L;

        // Call the controller method
        ResponseEntity<Void> response = menuItemController.removeMenuItem(menuItemId, token);

        // Assert that the service was called and response is 204
        verify(menuItemService, times(1)).removeMenuItem(menuItemId, token);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetMenuItemsByOwner_Success() {
        String token = "Bearer sampleToken";

        // Mock the service method call
        when(menuItemService.getMenuItemsByOwner(token)).thenReturn(Collections.singletonList(menuItemDTO));

        // Call the controller method
        ResponseEntity<List<MenuItemDTO>> response = menuItemController.getMenuItemsByOwner(token);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(menuItemDTO, response.getBody().get(0));
    }
}
