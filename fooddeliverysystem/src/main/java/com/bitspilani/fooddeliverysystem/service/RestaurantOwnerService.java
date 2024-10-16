package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.repository.RestaurantOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantOwnerService {

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    // Methods to manage restaurant owners
}