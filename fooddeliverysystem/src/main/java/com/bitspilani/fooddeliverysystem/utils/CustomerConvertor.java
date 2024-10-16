package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.model.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerConvertor {

    public static Customer toCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setMobileNo(customerDTO.getMobileNo());
        customer.setDeliveryAddress(AddressConvertor.toAddress(customerDTO.getAddress()));
        customer.setPaymentDetails(customerDTO.getPaymentDetails());
        return customer;
    }

    public static CustomerDTO toCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setUsername(customer.getUsername());
//        customerDTO.setPassword(customer.getPassword());
        customerDTO.setMobileNo(customer.getMobileNo());
        customerDTO.setAddress(AddressConvertor.toAddressDTO(customer.getDeliveryAddress()));
        customerDTO.setPaymentDetails(customer.getPaymentDetails());
        return customerDTO;
    }

    public static List<CustomerDTO> toCustomerDTOList(List<Customer> customers) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        customers.forEach(customer -> customerDTOList.add(toCustomerDTO(customer)));
        return customerDTOList;
    }
}
