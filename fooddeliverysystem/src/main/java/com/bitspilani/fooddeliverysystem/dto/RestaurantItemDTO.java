package com.bitspilani.fooddeliverysystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class RestaurantItemDTO {

    @Schema(description = "Restaurant id")
    @JsonProperty("restaurant_id")
    private Long id;

    @Schema(description = "Name of the restaurant.")
    @JsonProperty("restaurant_name")
    private String restaurantName;

    @Schema(description = "Address of the restaurant.")
    private AddressDTO address;

    @Schema(description = "Items available at the restaurant.")
    private List<MenuItemDTO> menuItems;
}
