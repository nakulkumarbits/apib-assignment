package com.bitspilani.fooddeliverysystem.dto;

import java.util.List;
import lombok.Data;

@Data
public class OrderDetailDTO {

  private List<Long> menuItemIds;
}
