package com.bitspilani.fooddeliverysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.UsernameMismatchException;
import com.bitspilani.fooddeliverysystem.model.DeliveryPersonnel;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.DeliveryPersonnelRepository;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class DeliveryPersonnelServiceTest {

    @Mock
    DeliveryPersonnelRepository deliveryPersonnelRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    DeliveryPersonnelService deliveryPersonnelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodeResponse");
    }

    @Test
    void testGetDeliveryPersonnel() {
        when(deliveryPersonnelRepository.findByUser(any(User.class))).thenReturn(getDeliveryPersonnel());
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(new User());

        DeliveryPersonnelDTO result = deliveryPersonnelService.getDeliveryPersonnel("username");
        assertEquals(new DeliveryPersonnelDTO(), result);
    }

    @Test
    void testGetCustomerWhenUserNotFound() {
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(null);
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> deliveryPersonnelService.getDeliveryPersonnel("username"));
        assertEquals(FoodDeliveryConstants.DELIVERY_PERSONNEL_NOT_PRESENT, exception.getMessage());
        verify(userRepository, times(1)).findByUsernameAndRole(anyString(), any());
    }

    @Test
    void testGetDeliveryPersonnels() {
        when(deliveryPersonnelRepository.findAll()).thenReturn(List.of(getDeliveryPersonnel()));
        List<DeliveryPersonnelDTO> result = deliveryPersonnelService.getDeliveryPersonnels();
        assertEquals(List.of(new DeliveryPersonnelDTO()), result);
    }

    @Test
    void testUpdateDeliveryPersonnel() {
        when(deliveryPersonnelRepository.findByUser(any(User.class))).thenReturn(getDeliveryPersonnel());
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(new User());
        when(deliveryPersonnelRepository.save(any())).thenReturn(getDeliveryPersonnel());

        DeliveryPersonnelDTO deliveryPersonnelDTO = new DeliveryPersonnelDTO();
        deliveryPersonnelDTO.setUsername("username");
        DeliveryPersonnelDTO result = deliveryPersonnelService.updateDeliveryPersonnel(deliveryPersonnelDTO, "username");
        assertEquals(new DeliveryPersonnelDTO(), result);
    }

    @Test
    void testUpdateDeliveryPersonnelUsernameMismatch() {
        UsernameMismatchException exception = assertThrows(UsernameMismatchException.class,
            () -> deliveryPersonnelService.updateDeliveryPersonnel(new DeliveryPersonnelDTO(), "username"));
        assertEquals(FoodDeliveryConstants.USERNAME_MISMATCH, exception.getMessage());
    }

    @Test
    void testUpdateDeliveryPersonnelWhenUserNotFound() {
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(null);
        DeliveryPersonnelDTO deliveryPersonnelDTO = new DeliveryPersonnelDTO();
        deliveryPersonnelDTO.setUsername("username");
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
            () -> deliveryPersonnelService.updateDeliveryPersonnel(deliveryPersonnelDTO, "username"));
        assertEquals(FoodDeliveryConstants.DELIVERY_PERSONNEL_NOT_PRESENT, exception.getMessage());
    }

    @Test
    void testGetDeliveryPersonnelByUsername() {
        when(userRepository.findByUsername("username")).thenReturn(new User());
        when(deliveryPersonnelRepository.findByUser(any(User.class))).thenReturn(getDeliveryPersonnel());
        DeliveryPersonnel deliveryPersonnel = deliveryPersonnelService.getDeliveryPersonnelByUsername("username");
        assertNotNull(deliveryPersonnel);
    }

    @Test
    void testGetDeliveryPersonnelByUsernameReturnsNull() {
        when(userRepository.findByUsername("username")).thenReturn(null);
        DeliveryPersonnel deliveryPersonnel = deliveryPersonnelService.getDeliveryPersonnelByUsername("username");
        assertNull(deliveryPersonnel);
    }

    private DeliveryPersonnel getDeliveryPersonnel() {
        DeliveryPersonnel deliveryPersonnel = new DeliveryPersonnel();
        deliveryPersonnel.setUser(new User());
        return deliveryPersonnel;
    }
}