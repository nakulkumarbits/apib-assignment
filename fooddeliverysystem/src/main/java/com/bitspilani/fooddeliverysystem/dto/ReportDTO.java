package com.bitspilani.fooddeliverysystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;

@Data
public class ReportDTO {

  @Schema(description = "Report type", example = "e.g., \"Most Popular Restaurants\", \"Average Delivery Time\", etc.")
  @JsonProperty("report_type")
  private String reportType;

  @JsonProperty("generated_date")
  private LocalDateTime generatedDate;

  @JsonProperty("report_data")
  private Map<String, Object> reportData;
}
