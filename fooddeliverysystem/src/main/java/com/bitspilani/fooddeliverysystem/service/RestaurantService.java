package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.RestaurantDTO;
import com.bitspilani.fooddeliverysystem.dto.RestaurantItemDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.UsernameMismatchException;
import com.bitspilani.fooddeliverysystem.model.MenuItem;
import com.bitspilani.fooddeliverysystem.model.Restaurant;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.MenuItemRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantRepository;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import com.bitspilani.fooddeliverysystem.utils.AddressConvertor;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import com.bitspilani.fooddeliverysystem.utils.MenuItemConvertor;
import com.bitspilani.fooddeliverysystem.utils.RestaurantConvertor;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RestaurantDTO getRestaurant(String username) {
        User user = userRepository.findByUsernameAndRole(username, UserRole.RESTAURANT_OWNER);
        if (user != null) {
            Restaurant restaurant = restaurantRepository.findByUser(user);
            return RestaurantConvertor.toDTO(restaurant);
        }
        throw new UserNotFoundException(FoodDeliveryConstants.RESTAURANT_NOT_PRESENT);
    }

    public List<RestaurantDTO> getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return RestaurantConvertor.toDTOList(restaurants);
    }

    public List<RestaurantItemDTO> getRestaurantsForCustomers() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<Long> restaurantIds = restaurants.stream().map(Restaurant::getId).toList();
        List<MenuItem> allMenuItems = menuItemRepository.findByRestaurantIdIn(restaurantIds);

        Map<Long, List<MenuItem>> menuItemsByRestaurantId = allMenuItems.stream()
            .collect(Collectors.groupingBy(menuItem -> menuItem.getRestaurant().getId()));

        return restaurants.stream()
            .map(restaurant -> {
                RestaurantItemDTO itemDTO = new RestaurantItemDTO();
                itemDTO.setRestaurantName(restaurant.getRestaurantName());
                itemDTO.setMenuItems(MenuItemConvertor.toDTOList(menuItemsByRestaurantId.get(restaurant.getId())));
                return itemDTO;
            })
            .collect(Collectors.toList());
    }

    public RestaurantDTO updateRestaurant(RestaurantDTO restaurantDTO, String username) {
        if (!username.equals(restaurantDTO.getUsername())) {
            throw new UsernameMismatchException(FoodDeliveryConstants.USERNAME_MISMATCH);
        }
        User user = userRepository.findByUsernameAndRole(username, UserRole.RESTAURANT_OWNER);
        if (user != null) {
            Restaurant restaurant = restaurantRepository.findByUser(user);
            copyFields(restaurant, restaurantDTO);
            Restaurant savedRestaurant = restaurantRepository.save(restaurant);
            return RestaurantConvertor.toDTO(savedRestaurant);
        }
        throw new UserNotFoundException(FoodDeliveryConstants.RESTAURANT_NOT_PRESENT);
    }

    public Restaurant getRestaurantByUsername(String username) {
        User user = userRepository.findByUsernameAndRole(username, UserRole.RESTAURANT_OWNER);
        if (user != null) {
            return restaurantRepository.findByUser(user);
        }
        return null;
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    private void copyFields(Restaurant restaurant, RestaurantDTO restaurantDTO) {
        restaurant.setHoursOfOperation(restaurantDTO.getHoursOfOperation());
        restaurant.setRestaurantAddress(AddressConvertor.toAddress(restaurantDTO.getAddress()));
        restaurant.getUser().setPassword(passwordEncoder.encode(restaurantDTO.getPassword()));
    }
}