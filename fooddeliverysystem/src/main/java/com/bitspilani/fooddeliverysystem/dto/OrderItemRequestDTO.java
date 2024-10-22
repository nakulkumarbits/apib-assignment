package com.bitspilani.fooddeliverysystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderItemRequestDTO {

  @Schema(description = "Id of the menu item.")
  @JsonProperty("menu_item_id")
  private long menuItemId;

  @Schema(description = "Quantity of the menu item.", example = "2")
  private int quantity;
}

