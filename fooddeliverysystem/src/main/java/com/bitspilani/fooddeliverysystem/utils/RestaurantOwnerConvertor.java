package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.dto.RestaurantOwnerDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.model.RestaurantOwner;
import com.bitspilani.fooddeliverysystem.model.User;

public class RestaurantOwnerConvertor {

    public static RestaurantOwner toRestaurantOwner(RestaurantOwnerDTO restaurantOwnerDTO) {
        RestaurantOwner restaurantOwner = new RestaurantOwner();
        restaurantOwner.setRestaurantName(restaurantOwnerDTO.getRestaurantName());
        restaurantOwner.setHoursOfOperation(restaurantOwnerDTO.getHoursOfOperation());
        restaurantOwner.setRestaurantAddress(AddressConvertor.toAddress(restaurantOwnerDTO.getAddress()));

        User user = new User();
        user.setUsername(restaurantOwnerDTO.getUsername());
        user.setPassword(restaurantOwnerDTO.getPassword());
        user.setRole(UserRole.RESTAURANT_OWNER);
        restaurantOwner.setUser(user);
        return restaurantOwner;
    }

    public static RestaurantOwnerDTO toRestaurantOwnerDTO(RestaurantOwner restaurantOwner) {
        RestaurantOwnerDTO restaurantOwnerDTO = new RestaurantOwnerDTO();
        restaurantOwnerDTO.setUsername(restaurantOwner.getUser().getUsername());
        restaurantOwnerDTO.setRestaurantName(restaurantOwner.getRestaurantName());
        restaurantOwnerDTO.setHoursOfOperation(restaurantOwner.getHoursOfOperation());
        restaurantOwnerDTO.setAddress(AddressConvertor.toAddressDTO(restaurantOwner.getRestaurantAddress()));
        return restaurantOwnerDTO;
    }

}
