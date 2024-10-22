package com.bitspilani.fooddeliverysystem.controller;

import com.bitspilani.fooddeliverysystem.dto.OrderRequestDTO;
import com.bitspilani.fooddeliverysystem.dto.OrderResponseDTO;
import com.bitspilani.fooddeliverysystem.dto.ValidationErrorResponse;
import com.bitspilani.fooddeliverysystem.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO,
      @RequestHeader("Authorization") String token) {
    return ResponseEntity.ok(orderService.createOrder(orderRequestDTO, token));
  }
}
