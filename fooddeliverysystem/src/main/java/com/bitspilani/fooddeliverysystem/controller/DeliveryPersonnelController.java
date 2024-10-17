package com.bitspilani.fooddeliverysystem.controller;

import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.dto.ValidationErrorResponse;
import com.bitspilani.fooddeliverysystem.service.DeliveryPersonnelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/delivery")
public class DeliveryPersonnelController {

    @Autowired
    private DeliveryPersonnelService deliveryPersonnelService;

    @Operation(summary = "Fetches a delivery personnel from the Food Delivery System.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delivery personnel was successfully fetched.", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation = DeliveryPersonnelDTO.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "400", description = "The error can be any of those: the provided username is not supported. " +
            "See the API spec for further details.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
    })
    @GetMapping("/{username}")
    public ResponseEntity<DeliveryPersonnelDTO> getDeliveryPersonnel(@PathVariable("username") String username) {
        return ResponseEntity.ok(deliveryPersonnelService.getDeliveryPersonnel(username));
    }

    @Operation(summary = "Updates a delivery personnel from the Food Delivery System.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delivery personnel was successfully updated.", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation = DeliveryPersonnelDTO.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "400", description = "The error can be any of those: the provided username is not supported. " +
            "Invalid input provided." +
            "See the API spec for further details.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
    })
    @PutMapping("/{username}")
    public ResponseEntity<DeliveryPersonnelDTO> updateDeliveryPersonnel(@PathVariable("username") String username, @Valid @RequestBody DeliveryPersonnelDTO deliveryPersonnelDTO) {
        return ResponseEntity.ok(deliveryPersonnelService.updateDeliveryPersonnel(deliveryPersonnelDTO, username));
    }
}
