package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.dto.DeactivateUserDTO;
import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.dto.RestaurantOwnerDTO;
import com.bitspilani.fooddeliverysystem.enums.UserStatus;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.AdministratorRepository;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {

    private final RestaurantOwnerService restaurantOwnerService;
    private AdministratorRepository administratorRepository;
    private CustomerService customerService;
    private DeliveryPersonnelService deliveryPersonnelService;
    private UserRepository userRepository;

    AdministratorService(AdministratorRepository administratorRepository,
        CustomerService customerService,
        DeliveryPersonnelService deliveryPersonnelService,
        UserRepository userRepository, RestaurantOwnerService restaurantOwnerService) {
        this.administratorRepository = administratorRepository;
        this.customerService = customerService;
        this.deliveryPersonnelService = deliveryPersonnelService;
        this.userRepository = userRepository;
        this.restaurantOwnerService = restaurantOwnerService;
    }

    public List<CustomerDTO> getCustomers() {
        return customerService.getCustomers();
    }

    public List<DeliveryPersonnelDTO> getDeliveryPersonnels() {
        return deliveryPersonnelService.getDeliveryPersonnels();
    }

    public List<RestaurantOwnerDTO> getRestaurantOwners() {
        return restaurantOwnerService.getRestaurantOwners();
    }

    public DeactivateUserDTO deactivateUser(DeactivateUserDTO deactivateUserDTO) {
        User user = userRepository.findByUsernameAndRole(deactivateUserDTO.getUsername(), deactivateUserDTO.getUserRole());
        if (user != null) {
            user.setStatus(UserStatus.DEACTIVATED);
            userRepository.save(user);

            deactivateUserDTO.setDeactivated(true);
            return deactivateUserDTO;
        }
        throw new UserNotFoundException("User not found.");
    }

    // Methods to manage admin tasks
}