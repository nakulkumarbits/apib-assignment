package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.dto.RestaurantDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.model.Restaurant;
import com.bitspilani.fooddeliverysystem.model.User;
import java.util.ArrayList;
import java.util.List;

public class RestaurantConvertor {

    public static Restaurant toEntity(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(restaurantDTO.getRestaurantName());
        restaurant.setHoursOfOperation(restaurantDTO.getHoursOfOperation());
        restaurant.setRestaurantAddress(AddressConvertor.toAddress(restaurantDTO.getAddress()));

        User user = new User();
        user.setUsername(restaurantDTO.getUsername());
        user.setPassword(restaurantDTO.getPassword());
        user.setRole(UserRole.RESTAURANT_OWNER);
        restaurant.setUser(user);
        return restaurant;
    }

    public static RestaurantDTO toDTO(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setUsername(restaurant.getUser().getUsername());
        restaurantDTO.setRestaurantName(restaurant.getRestaurantName());
        restaurantDTO.setHoursOfOperation(restaurant.getHoursOfOperation());
        restaurantDTO.setAddress(AddressConvertor.toAddressDTO(restaurant.getRestaurantAddress()));
        return restaurantDTO;
    }

    public static List<RestaurantDTO> toDTOList(List<Restaurant> restaurants) {
        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
        restaurants.forEach(restaurant -> restaurantDTOList.add(toDTO(restaurant)));
        return restaurantDTOList;
    }
}
