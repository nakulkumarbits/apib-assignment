package com.bitspilani.fooddeliverysystem.dto;

import lombok.Data;

@Data
public class OpeningHourDTO {

    private String day;
    private String openingTime;
    private String closingTime;
}