package com.bitspilani.fooddeliverysystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;

@Data
public class RestaurantOwnerDTO {

    @Schema(description = "The username of the user trying to register.")
    @NotBlank(message = "username is mandatory")
    @JsonProperty("username")
    private String username;

    @Schema(description = "The password of the user trying to register.")
    @NotBlank(message = "password is mandatory")
    @JsonProperty("password")
    private String password;

    @Schema(description = "The restaurant name of the user trying to register.")
    @NotBlank(message = "restaurant name is mandatory")
    @JsonProperty("restaurant_name")
    private String restaurantName;

    @Schema(description = "The address of the restaurant trying to register.")
    private AddressDTO address;

    @Schema(description = "hours of operation for the restaurant.")
    @JsonProperty("hours_of_operation")
    private String hoursOfOperation;

    @Schema(description = "Opening hours for the restaurant.")
    @JsonProperty("opening_hours")
    private List<OpeningHourDTO> openingHours;

    @Schema(description = "Delivery zones for the restaurant.")
    @JsonProperty("delivery_zones")
    private List<DeliveryZoneDTO> deliveryZones;
}
