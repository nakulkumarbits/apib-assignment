package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    // Methods to manage customers
}