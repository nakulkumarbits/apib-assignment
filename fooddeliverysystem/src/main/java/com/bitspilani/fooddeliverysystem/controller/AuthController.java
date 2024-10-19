package com.bitspilani.fooddeliverysystem.controller;

import com.bitspilani.fooddeliverysystem.dto.AdministratorDTO;
import com.bitspilani.fooddeliverysystem.dto.AuthRequest;
import com.bitspilani.fooddeliverysystem.dto.AuthResponse;
import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.dto.RestaurantOwnerDTO;
import com.bitspilani.fooddeliverysystem.dto.ValidationErrorResponse;
import com.bitspilani.fooddeliverysystem.model.BlacklistedToken;
import com.bitspilani.fooddeliverysystem.model.RestaurantOwner;
import com.bitspilani.fooddeliverysystem.repository.BlacklistedTokenRepository;
import com.bitspilani.fooddeliverysystem.security.JwtUtil;
import com.bitspilani.fooddeliverysystem.service.RestaurantOwnerService;
import com.bitspilani.fooddeliverysystem.service.UserService;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    @Operation(summary = "Register a customer to the Food Delivery System.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer was successfully registered.", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
        @ApiResponse(responseCode = "400", description = "The error can be any of those: the provided username is not supported, " +
            "the request is missing a required parameter, the provided password is invalid." +
            "See the API spec for further details.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
    })
    @PostMapping("/register/customer")
    public ResponseEntity<CustomerDTO> registerCustomer(@Parameter(description = "Request to register customer.") @RequestBody @Valid CustomerDTO customerDTO) {
        return ResponseEntity.ok(userService.registerCustomer(customerDTO));
    }

    @Operation(summary = "Restaurant owner can register a restaurant to the Food Delivery System.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurant was successfully registered.", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantOwnerDTO.class))),
        @ApiResponse(responseCode = "400", description = "The error can be any of those: the provided username is not supported, " +
            "the request is missing a required parameter, the provided password is invalid." +
            "See the API spec for further details.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
    })
    @PostMapping("/register/restaurant")
    public ResponseEntity<RestaurantOwnerDTO> registerRestaurantOwner(@Parameter(description = "Request to register restaurant owner.") @RequestBody @Valid RestaurantOwnerDTO owner) {
        return ResponseEntity.ok(userService.registerRestaurantOwner(owner));
    }

    @Operation(summary = "Register a delivery personnel to the Food Delivery System.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delivery Personnel was successfully registered.", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation = DeliveryPersonnelDTO.class))),
        @ApiResponse(responseCode = "400", description = "The error can be any of those: the provided username is not supported, " +
            "the request is missing a required parameter, the provided password is invalid." +
            "See the API spec for further details.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
    })
    @PostMapping("/register/delivery")
    public ResponseEntity<DeliveryPersonnelDTO> registerDeliveryPersonnel(
        @Parameter(description = "Request to register delivery personnel.") @RequestBody @Valid DeliveryPersonnelDTO deliveryPersonnelDTO) {
        return ResponseEntity.ok(userService.registerDeliveryPersonnel(deliveryPersonnelDTO));
    }

    @Operation(summary = "Register a administrator to the Food Delivery System.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrator was successfully registered.", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation = AdministratorDTO.class))),
        @ApiResponse(responseCode = "400", description = "The error can be any of those: the provided username is not supported, " +
            "the request is missing a required parameter, the provided password is invalid." +
            "See the API spec for further details.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
    })
    @PostMapping("/register/admin")
    public ResponseEntity<AdministratorDTO> registerAdministrator(
        @Parameter(description = "Request to register admin.") @RequestBody @Valid AdministratorDTO admin) {
        return ResponseEntity.ok(userService.registerAdministrator(admin));
    }

    @Operation(summary = "Logs in a user to Food Delivery System.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful login and token is issued.", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "400", description = "The error can be any of those: the provided username is not supported, " +
            "the request is missing a required parameter, the provided password is invalid." +
            "See the API spec for further details.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "401", description = "Login is not authorized.", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Parameter(description = "User login request containing username and password")
    @RequestBody @Valid AuthRequest authRequest) {
        try {
            // Authenticate the user
            Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

            Long ownerId = null;
            if (roles.contains(FoodDeliveryConstants.ROLE_RESTAURANT_OWNER)) {
                RestaurantOwner restaurantOwner = restaurantOwnerService.getRestaurantOwnerByUsername(authRequest.getUsername());
                ownerId = restaurantOwner.getId();
            }

            // Generate JWT token
            String token = jwtUtil.generateToken(authRequest.getUsername(), roles, ownerId);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }

    @Operation(summary = "Logs out a user from Food Delivery System.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logout is successful."),
        @ApiResponse(responseCode = "401", description = "Missing/Invalid Authorization header.")
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Parameter(description = "Auth header for which logout has to be done.")
    @RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            BlacklistedToken blacklistedToken = new BlacklistedToken();
            blacklistedToken.setToken(jwt);
            blacklistedToken.setExpiration(new Date(System.currentTimeMillis() + 86400000)); // Set expiration time
            blacklistedTokenRepository.save(blacklistedToken);
        }
        return ResponseEntity.ok("Logout successful");
    }


}
