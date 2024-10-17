package com.bitspilani.fooddeliverysystem.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.AdministratorDTO;
import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.dto.RestaurantOwnerDTO;
import com.bitspilani.fooddeliverysystem.model.Administrator;
import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.model.DeliveryPersonnel;
import com.bitspilani.fooddeliverysystem.model.RestaurantOwner;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.AdministratorRepository;
import com.bitspilani.fooddeliverysystem.repository.CustomerRepository;
import com.bitspilani.fooddeliverysystem.repository.DeliveryPersonnelRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantOwnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    RestaurantOwnerRepository restaurantOwnerRepository;
    @Mock
    DeliveryPersonnelRepository deliveryPersonnelRepository;
    @Mock
    AdministratorRepository administratorRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodeResponse");
    }

    @Test
    void testRegisterCustomer() {
        Customer customer = new Customer();
        customer.setUser(new User());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        CustomerDTO customerDTO = new CustomerDTO();
        CustomerDTO result = userService.registerCustomer(customerDTO);
        Assertions.assertEquals(customerDTO, result);
    }

    @Test
    void testRegisterRestaurantOwner() {
        RestaurantOwner restaurantOwner = new RestaurantOwner();
        restaurantOwner.setUser(new User());
        when(restaurantOwnerRepository.save(any(RestaurantOwner.class))).thenReturn(restaurantOwner);
        RestaurantOwnerDTO owner = new RestaurantOwnerDTO();
        RestaurantOwnerDTO result = userService.registerRestaurantOwner(owner);
        Assertions.assertEquals(owner, result);
    }

    @Test
    void testRegisterDeliveryPersonnel() {
        DeliveryPersonnel personnel = new DeliveryPersonnel();
        personnel.setUser(new User());
        when(deliveryPersonnelRepository.save(any(DeliveryPersonnel.class))).thenReturn(personnel);
        DeliveryPersonnelDTO deliveryPersonnelDTO = new DeliveryPersonnelDTO();
        DeliveryPersonnelDTO result = userService.registerDeliveryPersonnel(deliveryPersonnelDTO);
        Assertions.assertEquals(deliveryPersonnelDTO, result);
    }

    @Test
    void testRegisterAdministrator() {
        Administrator administrator = new Administrator();
        administrator.setUser(new User());
        when(administratorRepository.save(any(Administrator.class))).thenReturn(administrator);
        AdministratorDTO admin = new AdministratorDTO();
        AdministratorDTO result = userService.registerAdministrator(admin);
        Assertions.assertEquals(admin, result);
    }
}