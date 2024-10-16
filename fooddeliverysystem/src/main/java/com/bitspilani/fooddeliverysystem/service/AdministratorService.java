package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.CustomerDTO;
import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.repository.AdministratorRepository;
import com.bitspilani.fooddeliverysystem.repository.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {

    private AdministratorRepository administratorRepository;
    private CustomerRepository customerRepository;

    AdministratorService(AdministratorRepository administratorRepository,
        CustomerRepository customerRepository) {
        this.administratorRepository = administratorRepository;
        this.customerRepository = customerRepository;
    }

    public List<CustomerDTO> getCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return null;
    }

    // Methods to manage admin tasks
}