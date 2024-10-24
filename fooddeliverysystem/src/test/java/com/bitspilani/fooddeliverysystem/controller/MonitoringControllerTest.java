package com.bitspilani.fooddeliverysystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.PlatformActivityDTO;
import com.bitspilani.fooddeliverysystem.service.MonitoringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class MonitoringControllerTest {

    @Mock
    private MonitoringService monitoringService;

    @InjectMocks
    private MonitoringController monitoringController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPlatformActivity_Success() {
        // Mock the PlatformActivityDTO object
        PlatformActivityDTO platformActivityDTO = new PlatformActivityDTO();
        platformActivityDTO.setActiveUsers(100);

        // Mock the service call
        when(monitoringService.getPlatformActivity()).thenReturn(platformActivityDTO);

        // Perform the controller call
        ResponseEntity<PlatformActivityDTO> response = monitoringController.getPlatformActivity();

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(platformActivityDTO, response.getBody());
    }
}
