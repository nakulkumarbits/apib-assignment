package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.PlatformActivityDTO;
import com.bitspilani.fooddeliverysystem.enums.OrderStatus;
import com.bitspilani.fooddeliverysystem.model.OrderDetail;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.OrderDetailRepository;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {

  private final UserRepository userRepository;
  private final UserService userService;
  private final OrderDetailRepository orderDetailRepository;

  public MonitoringService(UserRepository userRepository, UserService userService, OrderDetailRepository orderDetailRepository) {
    this.userRepository = userRepository;
    this.userService = userService;
    this.orderDetailRepository = orderDetailRepository;
  }

  public PlatformActivityDTO getPlatformActivity() {
    PlatformActivityDTO platformActivity = new PlatformActivityDTO();
    platformActivity.setReportGeneratedAt(LocalDateTime.now());

    // Get active users (e.g., users who logged in within the past 30 minutes)
    List<User> activeUsers = userService.findActiveUsersWithinLastMinutes(30);
    platformActivity.setActiveUsers(activeUsers.size());

    // Get delivery activity (ongoing and completed deliveries)
    List<OrderDetail> ongoingAndCompletedDeliveries = orderDetailRepository.findByOrderStatusIn(
        List.of(OrderStatus.DELIVERED, OrderStatus.OUT_FOR_DELIVERY));

    List<OrderDetail> ongoingDeliveries = ongoingAndCompletedDeliveries.stream()
        .filter(orderDetail -> orderDetail.getOrderStatus().equals(OrderStatus.OUT_FOR_DELIVERY))
        .toList();

    List<OrderDetail> completedDeliveries = ongoingAndCompletedDeliveries.stream()
        .filter(orderDetail -> orderDetail.getOrderStatus().equals(OrderStatus.DELIVERED))
        .toList();

    Map<String, Long> deliveryActivity = Map.of(
        "Ongoing Deliveries", (long) ongoingDeliveries.size(),
        "Completed Deliveries", (long) completedDeliveries.size()
    );
    platformActivity.setDeliveryActivity(deliveryActivity);

    // Get count of orders by status
    List<OrderDetail> allOrders = orderDetailRepository.findAll();
    Map<String, Long> orderStatusCount = allOrders.stream()
        .collect(Collectors.groupingBy(order -> order.getOrderStatus().name(), Collectors.counting()));

    platformActivity.setOrderStatusCount(orderStatusCount);

    return platformActivity;
  }
}
