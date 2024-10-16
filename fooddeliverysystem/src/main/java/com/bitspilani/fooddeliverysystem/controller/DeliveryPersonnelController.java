package com.bitspilani.fooddeliverysystem.controller;

import com.bitspilani.fooddeliverysystem.service.DeliveryPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/delivery")
public class DeliveryPersonnelController {

    @Autowired
    private DeliveryPersonnelService deliveryPersonnelService;

    // Endpoints for managing delivery personnel
}
