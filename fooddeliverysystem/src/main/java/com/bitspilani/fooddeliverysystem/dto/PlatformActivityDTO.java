package com.bitspilani.fooddeliverysystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;

@Data
public class PlatformActivityDTO {

  @Schema(description = "Report generation time")
  @JsonProperty("report_generated_at")
  private LocalDateTime reportGeneratedAt;

  @Schema(description = "number of active users")
  @JsonProperty("active_users")
  private int activeUsers;

  @Schema(description = "delivery activities like ongoing and completed deliveries")
  @JsonProperty("delivery_activity")
  private Map<String, Long> deliveryActivity;

  @Schema(description = "order status map count based on statuses like preparing, delivered, etc.")
  @JsonProperty("order_status_count")
  private Map<String, Long> orderStatusCount;

}
