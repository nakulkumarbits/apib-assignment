package com.bitspilani.fooddeliverysystem.dto;

import com.bitspilani.fooddeliverysystem.enums.CuisineType;
import com.bitspilani.fooddeliverysystem.enums.ItemAvailable;
import com.bitspilani.fooddeliverysystem.enums.ItemType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MenuItemDTO {

    @JsonProperty("item_id")
    private Long itemId;

    @Schema(description = "Name of the dish.")
    @NotBlank(message = "item name is mandatory")
    private String name;

    @Schema(description = "Description of the dish.")
    private String description;

    @Schema(description = "Price of the dish.")
    @NotBlank(message = "price is mandatory")
    private Double price;

    @Schema(description = "Availability of the dish.")
    @JsonProperty("item_available")
    private ItemAvailable itemAvailable;

    @JsonProperty("item_type")
    private ItemType itemType;

    private CuisineType cuisine;
}
