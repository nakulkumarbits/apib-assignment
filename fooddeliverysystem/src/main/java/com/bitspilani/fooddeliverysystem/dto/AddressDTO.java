package com.bitspilani.fooddeliverysystem.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pinCode;
}
