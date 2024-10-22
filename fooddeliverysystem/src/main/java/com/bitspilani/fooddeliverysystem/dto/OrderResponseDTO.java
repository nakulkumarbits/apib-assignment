package com.bitspilani.fooddeliverysystem.dto;

import com.bitspilani.fooddeliverysystem.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponseDTO {

  private long orderId;

  @Schema(description = "First and last name of the customer")
  private String customerName;

  @Schema(description = "Restaurant name")
  @JsonProperty("restaurant_name")
  private String restaurantName;

  @Schema(description = "List of ordered items")
  @JsonProperty("ordered_items")
  private List<OrderItemResponseDTO> orderedItems;

  @Schema(description = "Total order amount")
  @JsonProperty("total_amount")
  private double totalAmount;

  @Schema(description = "Status of the order")
  @JsonProperty("order_status")
  private OrderStatus orderStatus;

  @Schema(description = "Date and time of the order")
  @JsonProperty("order_date")
  private LocalDateTime orderDate;
}
