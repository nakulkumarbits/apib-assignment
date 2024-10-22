package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.exceptions.UserNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.UsernameMismatchException;
import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.CustomerRepository;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import com.bitspilani.fooddeliverysystem.utils.AddressConvertor;
import com.bitspilani.fooddeliverysystem.utils.CustomerConvertor;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomerDTO getCustomer(String username) {
        User user = userRepository.findByUsernameAndRole(username, UserRole.CUSTOMER);
        if (user != null) {
            Customer customer = customerRepository.findByUser(user);
            return CustomerConvertor.toCustomerDTO(customer);
        }
        throw new UserNotFoundException(FoodDeliveryConstants.CUSTOMER_NOT_PRESENT);
    }

    public List<CustomerDTO> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return CustomerConvertor.toCustomerDTOList(customers);
    }

    public CustomerDTO updateCustomer(CustomerDTO customerDTO, String username) {
        if (!username.equals(customerDTO.getUsername())) {
            throw new UsernameMismatchException(FoodDeliveryConstants.USERNAME_MISMATCH);
        }
        User user = userRepository.findByUsernameAndRole(username, UserRole.CUSTOMER);
        if (user != null) {
            Customer customer = customerRepository.findByUser(user);
            copyFields(customer, customerDTO);
            Customer savedCustomer = customerRepository.save(customer);
            return CustomerConvertor.toCustomerDTO(savedCustomer);
        }
        throw new UserNotFoundException(FoodDeliveryConstants.CUSTOMER_NOT_PRESENT);
    }

    private void copyFields(Customer customer, CustomerDTO customerDTO) {
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setMobileNo(customerDTO.getMobileNo());
        customer.setEmail(customerDTO.getEmail());
        customer.setDeliveryAddress(AddressConvertor.toAddress(customerDTO.getAddress()));
        customer.getUser().setPassword(passwordEncoder.encode(customerDTO.getPassword()));
    }

    public Customer getCustomerByUsername(String username) {
        User user = userRepository.findByUsernameAndRole(username, UserRole.CUSTOMER);
        if (user != null) {
            return customerRepository.findByUser(user);
        }
        return null;
    }
}