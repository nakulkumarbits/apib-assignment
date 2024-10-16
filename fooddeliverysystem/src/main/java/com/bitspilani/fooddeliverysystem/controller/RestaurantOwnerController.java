package com.bitspilani.fooddeliverysystem.controller;

import com.bitspilani.fooddeliverysystem.service.RestaurantOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/restaurants")
public class RestaurantOwnerController {
    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    // Endpoints for managing restaurant owners
}