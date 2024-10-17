package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.AdministratorDTO;
import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.dto.RestaurantOwnerDTO;
import com.bitspilani.fooddeliverysystem.model.Administrator;
import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.model.DeliveryPersonnel;
import com.bitspilani.fooddeliverysystem.model.RestaurantOwner;
import com.bitspilani.fooddeliverysystem.repository.AdministratorRepository;
import com.bitspilani.fooddeliverysystem.repository.CustomerRepository;
import com.bitspilani.fooddeliverysystem.repository.DeliveryPersonnelRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantOwnerRepository;
import com.bitspilani.fooddeliverysystem.utils.AdminConvertor;
import com.bitspilani.fooddeliverysystem.utils.CustomerConvertor;
import com.bitspilani.fooddeliverysystem.utils.DeliveryPersonnelConvertor;
import com.bitspilani.fooddeliverysystem.utils.RestaurantOwnerConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    private DeliveryPersonnelRepository deliveryPersonnelRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerConvertor.toCustomer(customerDTO);
        customer.getUser().setPassword(passwordEncoder.encode(customer.getUser().getPassword()));
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerConvertor.toCustomerDTO(savedCustomer);
    }

    public RestaurantOwnerDTO registerRestaurantOwner(RestaurantOwnerDTO owner) {
        RestaurantOwner restaurantOwner = RestaurantOwnerConvertor.toRestaurantOwner(owner);
        restaurantOwner.getUser().setPassword(passwordEncoder.encode(restaurantOwner.getUser().getPassword()));
        RestaurantOwner savedOwner = restaurantOwnerRepository.save(restaurantOwner);
        return RestaurantOwnerConvertor.toRestaurantOwnerDTO(savedOwner);
    }

    public DeliveryPersonnelDTO registerDeliveryPersonnel(DeliveryPersonnelDTO deliveryPersonnelDTO) {
        DeliveryPersonnel personnel = DeliveryPersonnelConvertor.toDeliveryPersonnel(deliveryPersonnelDTO);
        personnel.getUser().setPassword(passwordEncoder.encode(personnel.getUser().getPassword()));
        DeliveryPersonnel deliveryPersonnel = deliveryPersonnelRepository.save(personnel);
        return DeliveryPersonnelConvertor.toDeliveryPersonnelDTO(deliveryPersonnel);
    }

    public AdministratorDTO registerAdministrator(AdministratorDTO admin) {
        Administrator administrator = AdminConvertor.toAdmin(admin);
        administrator.getUser().setPassword(passwordEncoder.encode(administrator.getUser().getPassword()));
        Administrator savedAdmin = administratorRepository.save(administrator);
        return AdminConvertor.toAdminDTO(savedAdmin);
    }
}
