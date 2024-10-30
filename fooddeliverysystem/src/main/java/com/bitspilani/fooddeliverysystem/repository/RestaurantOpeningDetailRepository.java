package com.bitspilani.fooddeliverysystem.repository;

import com.bitspilani.fooddeliverysystem.model.RestaurantOpeningDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantOpeningDetailRepository extends JpaRepository<RestaurantOpeningDetail, Long> {

    List<RestaurantOpeningDetail> findByRestaurantId(Long restaurantId);
}
