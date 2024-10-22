package com.bitspilani.fooddeliverysystem.dto;

import com.bitspilani.fooddeliverysystem.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.Data;

@Data
public class OrderRequestDTO {

  @Schema(description = "Id of the customer placing the order.")
  @Min(value = 1, message = "Customer id cannot be negative.")
  @JsonProperty("customer_id")
  private long customerId;

  @Schema(description = "Id of the restaurant from where the order is being placed.")
  @JsonProperty("restaurant_id")
  @Min(value = 1, message = "Restaurant id cannot be negative.")
  private long restaurantId;

  @Schema(description = "Items present in the order.")
  @JsonProperty("order_items")
  private List<OrderItemRequestDTO> orderItems;

  @Schema(description = "method how the payment will be made.", example = "UPI")
  @JsonProperty("payment_method")
  private PaymentMethod paymentMethod;
}
