package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.RestaurantOwnerDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.UsernameMismatchException;
import com.bitspilani.fooddeliverysystem.model.RestaurantOwner;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.RestaurantOwnerRepository;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import com.bitspilani.fooddeliverysystem.utils.AddressConvertor;
import com.bitspilani.fooddeliverysystem.utils.RestaurantOwnerConvertor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RestaurantOwnerService {

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RestaurantOwnerDTO getRestaurantOwner(String username) {
        User user = userRepository.findByUsernameAndRole(username, UserRole.RESTAURANT_OWNER);
        if (user != null) {
            RestaurantOwner restaurantOwner = restaurantOwnerRepository.findByUser(user);
            return RestaurantOwnerConvertor.toRestaurantOwnerDTO(restaurantOwner);
        }
        throw new UserNotFoundException("Restaurant does not exist.");
    }

    public List<RestaurantOwnerDTO> getRestaurantOwners() {
        List<RestaurantOwner> restaurantOwners = restaurantOwnerRepository.findAll();
        return RestaurantOwnerConvertor.toRestaurantOwnerDTOList(restaurantOwners);
    }

    public RestaurantOwnerDTO updateRestaurantOwner(RestaurantOwnerDTO restaurantOwnerDTO, String username) {
        if (!username.equals(restaurantOwnerDTO.getUsername())) {
            throw new UsernameMismatchException("Username mismatch in request and body.");
        }
        User user = userRepository.findByUsernameAndRole(username, UserRole.RESTAURANT_OWNER);
        if (user != null) {
            RestaurantOwner restaurantOwner = restaurantOwnerRepository.findByUser(user);
            copyFields(restaurantOwner, restaurantOwnerDTO);
            RestaurantOwner savedRestaurantOwner = restaurantOwnerRepository.save(restaurantOwner);
            return RestaurantOwnerConvertor.toRestaurantOwnerDTO(savedRestaurantOwner);
        }
        throw new UserNotFoundException("Restaurant does not exist.");
    }

    private void copyFields(RestaurantOwner restaurantOwner, RestaurantOwnerDTO restaurantOwnerDTO) {
        restaurantOwner.setHoursOfOperation(restaurantOwnerDTO.getHoursOfOperation());
        restaurantOwner.setRestaurantAddress(AddressConvertor.toAddress(restaurantOwnerDTO.getAddress()));
        restaurantOwner.getUser().setPassword(passwordEncoder.encode(restaurantOwnerDTO.getPassword()));
    }
}