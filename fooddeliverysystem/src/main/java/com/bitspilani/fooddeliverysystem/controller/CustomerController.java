package com.bitspilani.fooddeliverysystem.controller;

import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String init() {
        return "Hello World!";
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(Long id) {
        return null;
    }

}
