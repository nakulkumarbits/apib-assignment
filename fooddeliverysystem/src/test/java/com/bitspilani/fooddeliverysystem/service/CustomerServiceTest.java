package com.bitspilani.fooddeliverysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.UsernameMismatchException;
import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.CustomerRepository;
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

class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodeResponse");
    }

    @Test
    void testGetCustomer() {
        Customer customer = getCustomer();
        when(customerRepository.findByUser(any(User.class))).thenReturn(customer);
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(new User());
        CustomerDTO result = customerService.getCustomer("username");
        Assertions.assertEquals(new CustomerDTO(), result);
    }

    @Test
    void testGetCustomerWhenUserNotFound() {
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(null);
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> customerService.getCustomer("username"));
        assertEquals(FoodDeliveryConstants.CUSTOMER_NOT_PRESENT, exception.getMessage());
        verify(userRepository, times(1)).findByUsernameAndRole(anyString(), any());
    }

    @Test
    void testGetCustomers() {
        when(customerRepository.findAll()).thenReturn(List.of(getCustomer()));
        List<CustomerDTO> result = customerService.getCustomers();
        Assertions.assertEquals(List.of(new CustomerDTO()), result);
    }

    @Test
    void testUpdateCustomer() {
        when(customerRepository.findByUser(any(User.class))).thenReturn(getCustomer());
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(new User());
        when(customerRepository.save(any())).thenReturn(getCustomer());

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUsername("username");
        CustomerDTO result = customerService.updateCustomer(customerDTO, "username");
        Assertions.assertEquals(new CustomerDTO(), result);
    }

    @Test
    void testUpdateCustomerUsernameMismatch() {
        UsernameMismatchException exception = assertThrows(UsernameMismatchException.class,
            () -> customerService.updateCustomer(new CustomerDTO(), "username"));
        assertEquals(FoodDeliveryConstants.USERNAME_MISMATCH, exception.getMessage());
    }

    @Test
    void testUpdateCustomerWhenUserNotFound() {
        when(userRepository.findByUsernameAndRole(anyString(), any(UserRole.class))).thenReturn(null);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUsername("username");
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
            () -> customerService.updateCustomer(customerDTO, "username"));
        assertEquals(FoodDeliveryConstants.CUSTOMER_NOT_PRESENT, exception.getMessage());
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setUser(new User());
        return customer;
    }
}