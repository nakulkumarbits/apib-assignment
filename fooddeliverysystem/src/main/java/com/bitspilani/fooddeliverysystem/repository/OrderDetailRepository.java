package com.bitspilani.fooddeliverysystem.repository;

import com.bitspilani.fooddeliverysystem.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
