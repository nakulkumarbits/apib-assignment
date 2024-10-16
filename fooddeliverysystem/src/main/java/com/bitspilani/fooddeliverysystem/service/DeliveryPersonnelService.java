package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.repository.DeliveryPersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPersonnelService {
    @Autowired
    private DeliveryPersonnelRepository deliveryPersonnelRepository;

    // Methods to manage delivery personnel
}