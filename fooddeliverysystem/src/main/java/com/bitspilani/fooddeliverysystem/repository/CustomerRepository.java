package com.bitspilani.fooddeliverysystem.repository;

import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByUser(User user);
}
