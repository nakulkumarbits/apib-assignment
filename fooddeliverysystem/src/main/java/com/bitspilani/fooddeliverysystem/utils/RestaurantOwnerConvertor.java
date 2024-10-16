package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.dto.RestaurantOwnerDTO;
import com.bitspilani.fooddeliverysystem.model.RestaurantOwner;

public class RestaurantOwnerConvertor {

    public static RestaurantOwner toRestaurantOwner(RestaurantOwnerDTO restaurantOwnerDTO) {
        RestaurantOwner restaurantOwner = new RestaurantOwner();
        restaurantOwner.setUsername(restaurantOwnerDTO.getUsername());
        restaurantOwner.setPassword(restaurantOwnerDTO.getPassword());
        restaurantOwner.setRestaurantName(restaurantOwnerDTO.getRestaurantName());
        restaurantOwner.setHoursOfOperation(restaurantOwnerDTO.getHoursOfOperation());
        restaurantOwner.setRestaurantAddress(AddressConvertor.toAddress(restaurantOwnerDTO.getAddress()));
        return restaurantOwner;
    }

    public static RestaurantOwnerDTO toRestaurantOwnerDTO(RestaurantOwner restaurantOwner) {
        RestaurantOwnerDTO restaurantOwnerDTO = new RestaurantOwnerDTO();
        restaurantOwnerDTO.setUsername(restaurantOwner.getUsername());
//        restaurantOwnerDTO.setPassword(restaurantOwner.getPassword());
        restaurantOwnerDTO.setRestaurantName(restaurantOwner.getRestaurantName());
        restaurantOwnerDTO.setHoursOfOperation(restaurantOwner.getHoursOfOperation());
        restaurantOwnerDTO.setAddress(AddressConvertor.toAddressDTO(restaurantOwner.getRestaurantAddress()));
        return restaurantOwnerDTO;
    }

}
