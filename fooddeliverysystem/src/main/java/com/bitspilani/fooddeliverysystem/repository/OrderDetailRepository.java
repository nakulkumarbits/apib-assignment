package com.bitspilani.fooddeliverysystem.repository;

import com.bitspilani.fooddeliverysystem.enums.OrderStatus;
import com.bitspilani.fooddeliverysystem.model.OrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

  List<OrderDetail> findByCustomerIdOrderByCreatedDateDesc(Long customerId);

  List<OrderDetail> findByRestaurantIdAndOrderStatusIn(Long restaurantId, List<OrderStatus> statuses);

  List<OrderDetail> findByOrderStatusIn(List<OrderStatus> statuses);
}
