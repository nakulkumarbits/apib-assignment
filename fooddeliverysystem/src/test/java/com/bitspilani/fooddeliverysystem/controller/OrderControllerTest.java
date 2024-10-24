package com.bitspilani.fooddeliverysystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.DeliveryResponseDTO;
import com.bitspilani.fooddeliverysystem.dto.OrderRequestDTO;
import com.bitspilani.fooddeliverysystem.dto.OrderResponseDTO;
import com.bitspilani.fooddeliverysystem.enums.OrderStatus;
import com.bitspilani.fooddeliverysystem.service.OrderService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private OrderRequestDTO orderRequestDTO;
    private OrderResponseDTO orderResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize sample OrderRequestDTO and OrderResponseDTO
        orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setRestaurantId(1L);

        orderResponseDTO = new OrderResponseDTO();
    }

    @Test
    void testCreateOrder_Success() {
        String token = "Bearer sampleToken";

        // Mock the service method call
        when(orderService.createOrder(orderRequestDTO, token)).thenReturn(orderResponseDTO);

        // Call the controller method
        ResponseEntity<OrderResponseDTO> response = orderController.createOrder(orderRequestDTO, token);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderResponseDTO, response.getBody());
    }

    @Test
    void testGetOrder_Success() {
        Long orderId = 1L;

        // Mock the service method call
        when(orderService.getOrder(orderId)).thenReturn(orderResponseDTO);

        // Call the controller method
        ResponseEntity<OrderResponseDTO> response = orderController.getOrder(orderId);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderResponseDTO, response.getBody());
    }

    @Test
    void testUpdateOrderStatus_Success() {
        Long orderId = 1L;
        String token = "Bearer sampleToken";
        OrderStatus newStatus = OrderStatus.DELIVERED;

        // Mock the service method call
        when(orderService.updateOrderStatus(orderId, newStatus, token)).thenReturn(orderResponseDTO);

        // Call the controller method
        ResponseEntity<OrderResponseDTO> response = orderController.updateOrderStatus(orderId, newStatus, token);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderResponseDTO, response.getBody());
    }

    @Test
    void testGetOrders_Success() {
        String token = "Bearer sampleToken";

        // Mock the service method call
        when(orderService.getOrders(token)).thenReturn(Collections.singletonList(orderResponseDTO));

        // Call the controller method
        ResponseEntity<List<OrderResponseDTO>> response = orderController.getOrders(token);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(orderResponseDTO, response.getBody().get(0));
    }

    @Test
    void testGetIncomingOrders_Success() {
        String token = "Bearer sampleToken";

        // Mock the service method call
        when(orderService.getIncomingOrders(token)).thenReturn(Collections.singletonList(orderResponseDTO));

        // Call the controller method
        ResponseEntity<List<OrderResponseDTO>> response = orderController.getIncomingOrders(token);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(orderResponseDTO, response.getBody().get(0));
    }

    @Test
    void testGetDeliverables_Success() {
        String token = "Bearer sampleToken";
        DeliveryResponseDTO deliveryResponseDTO = new DeliveryResponseDTO();
        deliveryResponseDTO.setOrderId(1L);

        // Mock the service method call
        when(orderService.getDeliverables(token)).thenReturn(Collections.singletonList(deliveryResponseDTO));

        // Call the controller method
        ResponseEntity<List<DeliveryResponseDTO>> response = orderController.getDeliverables(token);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(deliveryResponseDTO, response.getBody().get(0));
    }
}
