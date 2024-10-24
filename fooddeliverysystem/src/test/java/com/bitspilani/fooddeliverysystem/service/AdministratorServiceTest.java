package com.bitspilani.fooddeliverysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.dto.DeactivateUserDTO;
import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.dto.RestaurantDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.AdministratorRepository;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AdministratorServiceTest {

    @Mock
    RestaurantService restaurantService;
    @Mock
    AdministratorRepository administratorRepository;
    @Mock
    CustomerService customerService;
    @Mock
    DeliveryPersonnelService deliveryPersonnelService;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    AdministratorService administratorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomers() {
        when(customerService.getCustomers()).thenReturn(List.of(new CustomerDTO()));

        List<CustomerDTO> result = administratorService.getCustomers();
        assertEquals(List.of(new CustomerDTO()), result);
    }

    @Test
    void testGetDeliveryPersonnels() {
        when(deliveryPersonnelService.getDeliveryPersonnels()).thenReturn(List.of(new DeliveryPersonnelDTO()));

        List<DeliveryPersonnelDTO> result = administratorService.getDeliveryPersonnels();
        assertEquals(List.of(new DeliveryPersonnelDTO()), result);
    }

    @Test
    void testGetRestaurants() {
        when(restaurantService.getRestaurants()).thenReturn(List.of(new RestaurantDTO()));

        List<RestaurantDTO> result = administratorService.getRestaurants();
        assertEquals(List.of(new RestaurantDTO()), result);
    }

    @Test
    void testDeactivateUser() {
        when(userRepository.findByUsernameAndRole(anyString(), any())).thenReturn(new User());
        DeactivateUserDTO deactivateUserDTO = new DeactivateUserDTO();
        deactivateUserDTO.setUsername(RandomStringUtils.random(10));
        deactivateUserDTO.setUserRole(UserRole.CUSTOMER);
        DeactivateUserDTO result = administratorService.deactivateUser(deactivateUserDTO);
        assertEquals(deactivateUserDTO, result);
    }

    @Test
    void testDeactivateUserWhenUserNotFound() {
        when(userRepository.findByUsernameAndRole(anyString(), any())).thenReturn(null);
        DeactivateUserDTO deactivateUserDTO = new DeactivateUserDTO();
        deactivateUserDTO.setUsername(RandomStringUtils.random(10));
        deactivateUserDTO.setUserRole(UserRole.CUSTOMER);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> administratorService.deactivateUser(deactivateUserDTO));
        assertEquals(FoodDeliveryConstants.USER_NOT_PRESENT, exception.getMessage());
        verify(userRepository, times(1)).findByUsernameAndRole(anyString(), any());
    }
}