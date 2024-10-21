package com.bitspilani.fooddeliverysystem.repository;

import com.bitspilani.fooddeliverysystem.model.MenuItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long>,
    JpaSpecificationExecutor<MenuItem> {

  List<MenuItem> findByRestaurantId(Long restaurantId);

  List<MenuItem> findByRestaurantIdIn(List<Long> restaurantIds);
}