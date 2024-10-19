package com.bitspilani.fooddeliverysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.AddressDTO;
import com.bitspilani.fooddeliverysystem.dto.AdministratorDTO;
import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.dto.DeliveryZoneDTO;
import com.bitspilani.fooddeliverysystem.dto.OpeningHourDTO;
import com.bitspilani.fooddeliverysystem.dto.RestaurantOwnerDTO;
import com.bitspilani.fooddeliverysystem.model.Address;
import com.bitspilani.fooddeliverysystem.model.Administrator;
import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.model.DeliveryPersonnel;
import com.bitspilani.fooddeliverysystem.model.RestaurantDeliveryZone;
import com.bitspilani.fooddeliverysystem.model.RestaurantOpeningDetail;
import com.bitspilani.fooddeliverysystem.model.RestaurantOwner;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.AdministratorRepository;
import com.bitspilani.fooddeliverysystem.repository.CustomerRepository;
import com.bitspilani.fooddeliverysystem.repository.DeliveryPersonnelRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantDeliveryZoneRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantOpeningDetailRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantOwnerRepository;
import java.util.List;
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
    RestaurantOpeningDetailRepository restaurantOpeningDetailRepository;
    @Mock
    RestaurantDeliveryZoneRepository restaurantDeliveryZoneRepository;
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
        customer.setDeliveryAddress(new Address());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setAddress(new AddressDTO());
        CustomerDTO result = userService.registerCustomer(customerDTO);
        assertNotNull(result);
    }

    @Test
    void testRegisterRestaurantOwner() {
        RestaurantOwner restaurantOwner = new RestaurantOwner();
        restaurantOwner.setUser(new User());
        when(restaurantOwnerRepository.save(any(RestaurantOwner.class))).thenReturn(restaurantOwner);
        when(restaurantOpeningDetailRepository.saveAll(any())).thenReturn(List.of(new RestaurantOpeningDetail()));
        when(restaurantDeliveryZoneRepository.saveAll(any())).thenReturn(List.of(new RestaurantDeliveryZone()));
        RestaurantOwnerDTO owner = new RestaurantOwnerDTO();
        owner.setOpeningHours(List.of(new OpeningHourDTO()));
        owner.setDeliveryZones(List.of(new DeliveryZoneDTO()));
        RestaurantOwnerDTO result = userService.registerRestaurantOwner(owner);
        assertNotNull(result);
    }

    @Test
    void testRegisterDeliveryPersonnel() {
        DeliveryPersonnel personnel = new DeliveryPersonnel();
        personnel.setUser(new User());
        when(deliveryPersonnelRepository.save(any(DeliveryPersonnel.class))).thenReturn(personnel);
        DeliveryPersonnelDTO deliveryPersonnelDTO = new DeliveryPersonnelDTO();
        DeliveryPersonnelDTO result = userService.registerDeliveryPersonnel(deliveryPersonnelDTO);
        assertEquals(deliveryPersonnelDTO, result);
    }

    @Test
    void testRegisterAdministrator() {
        Administrator administrator = new Administrator();
        administrator.setUser(new User());
        when(administratorRepository.save(any(Administrator.class))).thenReturn(administrator);
        AdministratorDTO admin = new AdministratorDTO();
        AdministratorDTO result = userService.registerAdministrator(admin);
        assertEquals(admin, result);
    }
}