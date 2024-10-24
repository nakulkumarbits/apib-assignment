package com.bitspilani.fooddeliverysystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.ReportDTO;
import com.bitspilani.fooddeliverysystem.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    private ReportDTO reportDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock a sample ReportDTO
        reportDTO = new ReportDTO();
        reportDTO.setReportType("Sample Report");
    }

    @Test
    void testGeneratePopularRestaurantsReport_Success() {
        // Mock the service method call
        when(reportService.generatePopularRestaurantsReport()).thenReturn(reportDTO);

        // Call the controller method
        ResponseEntity<ReportDTO> response = reportController.generatePopularRestaurantsReport();

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reportDTO, response.getBody());
    }

    @Test
    void testGenerateAverageDeliveryTimeReport_Success() {
        // Mock the service method call
        when(reportService.generateAverageDeliveryTimeReport()).thenReturn(reportDTO);

        // Call the controller method
        ResponseEntity<ReportDTO> response = reportController.generateAverageDeliveryTimeReport();

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reportDTO, response.getBody());
    }

    @Test
    void testGenerateOrderTrendsReport_Success() {
        String period = "monthly";

        // Mock the service method call
        when(reportService.generateOrderTrendsReport(period)).thenReturn(reportDTO);

        // Call the controller method
        ResponseEntity<ReportDTO> response = reportController.generateOrderTrendsReport(period);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reportDTO, response.getBody());
    }
}
