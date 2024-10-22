package com.bitspilani.fooddeliverysystem.controller;

import com.bitspilani.fooddeliverysystem.dto.OrderRequestDTO;
import com.bitspilani.fooddeliverysystem.dto.OrderResponseDTO;
import com.bitspilani.fooddeliverysystem.dto.ValidationErrorResponse;
import com.bitspilani.fooddeliverysystem.enums.OrderStatus;
import com.bitspilani.fooddeliverysystem.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Operation(summary = "Place an order on the Food Delivery System.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order was successfully placed.", content =
      @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "400", description =
          "The error can be any of those: the provided input is not supported. " +
              "See the API spec for further details.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
  })
  @PostMapping
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO,
      @RequestHeader("Authorization") String token) {
    return ResponseEntity.ok(orderService.createOrder(orderRequestDTO, token));
  }

  @Operation(summary = "Fetches an order from the Food Delivery System.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order was successfully retrieved.", content =
      @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "400", description = "See the API spec for further details.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
  })
  @GetMapping("/{orderId}")
  @PreAuthorize("hasAnyRole('CUSTOMER','RESTAURANT_OWNER', 'ADMIN')")
  public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long orderId) {
    return ResponseEntity.ok(orderService.getOrder(orderId));
  }

  @Operation(summary = "Updates the order status of an order on the Food Delivery System.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order status was successfully updated.", content =
      @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "400", description =
          "The error can be any of those: the provided input is not supported. " +
              "See the API spec for further details.",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
  })
  @PutMapping("/{orderId}/status")
  @PreAuthorize("hasAnyRole('RESTAURANT_OWNER', 'ADMIN')")
  public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long orderId,
      @RequestParam OrderStatus newStatus) {
    return ResponseEntity.ok(orderService.updateOrderStatus(orderId, newStatus));
  }
}
