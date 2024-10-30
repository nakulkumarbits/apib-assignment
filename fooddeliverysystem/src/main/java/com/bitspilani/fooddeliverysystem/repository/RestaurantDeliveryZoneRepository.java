package com.bitspilani.fooddeliverysystem.repository;

import com.bitspilani.fooddeliverysystem.model.RestaurantDeliveryZone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantDeliveryZoneRepository extends JpaRepository<RestaurantDeliveryZone, Long> {

    List<RestaurantDeliveryZone> findByRestaurantId(Long restaurantId);
}
