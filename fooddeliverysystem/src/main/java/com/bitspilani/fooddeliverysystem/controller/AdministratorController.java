package com.bitspilani.fooddeliverysystem.controller;

import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.dto.DeactivateUserDTO;
import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.dto.RestaurantOwnerDTO;
import com.bitspilani.fooddeliverysystem.dto.ValidationErrorResponse;
import com.bitspilani.fooddeliverysystem.service.AdministratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Validated
public class AdministratorController {

    private final AdministratorService administratorService;

    AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @Operation(summary = "Fetches all the customers from the Food Delivery System.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customers were successfully fetched.", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        return new ResponseEntity<>(administratorService.getCustomers(), HttpStatus.OK);
    }

    @Operation(summary = "Deactivates a user from the Food Delivery System.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User was successfully fetched.", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation = DeactivateUserDTO.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "400", description = "The error can be any of those: the provided username is not supported. " +
            "See the API spec for further details.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
    })
    @PostMapping("/deactivate")
    public ResponseEntity<?> deactivateUser(@Valid @RequestBody DeactivateUserDTO deactivateUserDTO) {
        return ResponseEntity.ok(administratorService.deactivateUser(deactivateUserDTO));
    }

    @Operation(summary = "Fetches all the delivery personnel from the Food Delivery System.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delivery personnel were successfully fetched.", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation = DeliveryPersonnelDTO.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/delivery")
    public ResponseEntity<List<DeliveryPersonnelDTO>> getDeliveryPersonnels() {
        return new ResponseEntity<>(administratorService.getDeliveryPersonnels(), HttpStatus.OK);
    }

    @Operation(summary = "Fetches all the restaurants from the Food Delivery System.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurants were successfully fetched.", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantOwnerDTO.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantOwnerDTO>> getRestaurantOwners() {
        return new ResponseEntity<>(administratorService.getRestaurantOwners(), HttpStatus.OK);
    }
}