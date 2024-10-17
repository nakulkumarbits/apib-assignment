package com.bitspilani.fooddeliverysystem.repository;

import com.bitspilani.fooddeliverysystem.model.DeliveryPersonnel;
import com.bitspilani.fooddeliverysystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPersonnelRepository extends JpaRepository<DeliveryPersonnel, Long> {

    DeliveryPersonnel findByUser(User user);
}