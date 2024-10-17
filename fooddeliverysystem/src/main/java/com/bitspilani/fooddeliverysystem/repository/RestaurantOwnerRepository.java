package com.bitspilani.fooddeliverysystem.repository;

import com.bitspilani.fooddeliverysystem.model.RestaurantOwner;
import com.bitspilani.fooddeliverysystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Long> {

    RestaurantOwner findByUser(User user);
}