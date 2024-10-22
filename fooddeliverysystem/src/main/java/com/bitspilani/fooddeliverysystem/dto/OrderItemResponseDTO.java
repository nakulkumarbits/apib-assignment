package com.bitspilani.fooddeliverysystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderItemResponseDTO {

  @Schema(description = "Name of the menu item.")
  @JsonProperty("item_name")
  private String itemName;

  @Schema(description = "Quantity ordered.")
  @JsonProperty("quantity")
  private int quantity;

  @Schema(description = "Price per unit.")
  @JsonProperty("price")
  private double price;

  @Schema(description = "Total price for this item (price * quantity).")
  @JsonProperty("total_price")
  private double totalPrice;
}
