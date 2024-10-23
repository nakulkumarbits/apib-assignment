package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.ReportDTO;
import com.bitspilani.fooddeliverysystem.enums.OrderStatus;
import com.bitspilani.fooddeliverysystem.model.OrderDetail;
import com.bitspilani.fooddeliverysystem.repository.OrderDetailRepository;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

  private final OrderDetailRepository orderDetailRepository;

  public ReportService(OrderDetailRepository orderDetailRepository) {
    this.orderDetailRepository = orderDetailRepository;
  }

  public ReportDTO generatePopularRestaurantsReport() {
    List<OrderDetail> orderDetails = orderDetailRepository.findAll();

    // Group orders by restaurant and count them
    Map<String, Long> restaurantOrderCount = orderDetails.stream()
        .collect(Collectors.groupingBy(order -> order.getRestaurant().getRestaurantName(), Collectors.counting()));

    // Sort restaurants by the number of orders in descending order
    LinkedHashMap<String, Long> sortedRestaurantOrderCount = restaurantOrderCount.entrySet().stream()
        .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (e1, e2) -> e1,
            LinkedHashMap::new
        ));

    ReportDTO report = new ReportDTO();
    report.setReportType(FoodDeliveryConstants.MOST_POPULAR_RESTAURANTS);
    report.setGeneratedDate(LocalDateTime.now());
    report.setReportData(Map.of(FoodDeliveryConstants.RESTAURANT_ORDERS, sortedRestaurantOrderCount));

    return report;
  }

  public ReportDTO generateAverageDeliveryTimeReport() {
    List<OrderDetail> deliveredOrders = orderDetailRepository.findByOrderStatus(OrderStatus.DELIVERED);

    // Calculate the average time difference between order creation and delivery
    OptionalDouble averageDeliveryTime = deliveredOrders.stream()
        .mapToDouble(order -> Duration.between(order.getCreatedDate(), order.getModifiedDate()).toMinutes())
        .average();

    ReportDTO report = new ReportDTO();
    report.setReportType(FoodDeliveryConstants.AVERAGE_DELIVERY_TIME);
    report.setGeneratedDate(LocalDateTime.now());
    report.setReportData(Map.of(FoodDeliveryConstants.AVERAGE_DELIVERY_TIME_MINUTES, averageDeliveryTime.orElse(0.0)));

    return report;
  }

  public ReportDTO generateOrderTrendsReport(String period) {
    LocalDateTime startDate;

    switch (period.toLowerCase()) {
      case FoodDeliveryConstants.DAILY:
        startDate = LocalDateTime.now().minusDays(1);
        break;
      case FoodDeliveryConstants.WEEKLY:
        startDate = LocalDateTime.now().minusWeeks(1);
        break;
      case FoodDeliveryConstants.MONTHLY:
        startDate = LocalDateTime.now().minusMonths(1);
        break;
      default:
        throw new IllegalArgumentException("Invalid period: " + period);
    }

    List<OrderDetail> orderDetails = orderDetailRepository.findByCreatedDateAfter(startDate);

    // Group orders by date and count them
    Map<LocalDateTime, Long> orderTrends = orderDetails.stream()
        .collect(
            Collectors.groupingBy(order -> order.getCreatedDate().toLocalDate().atStartOfDay(), Collectors.counting()));

    ReportDTO report = new ReportDTO();
    report.setReportType("Order Trends (" + period + ")");
    report.setGeneratedDate(LocalDateTime.now());
    report.setReportData(Map.of(FoodDeliveryConstants.ORDER_TRENDS, orderTrends));

    return report;
  }
}
