package com.bitspilani.fooddeliverysystem.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.PlatformActivityDTO;
import com.bitspilani.fooddeliverysystem.enums.OrderStatus;
import com.bitspilani.fooddeliverysystem.model.OrderDetail;
import com.bitspilani.fooddeliverysystem.model.User;
import com.bitspilani.fooddeliverysystem.repository.OrderDetailRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MonitoringServiceTest {

  @Mock
  UserService userService;
  @Mock
  OrderDetailRepository orderDetailRepository;
  @InjectMocks
  MonitoringService monitoringService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetPlatformActivity() {
    List<User> users = List.of(new User(), new User());
    when(userService.findActiveUsersWithinLastMinutes(anyInt())).thenReturn(users);
    when(orderDetailRepository.findByOrderStatusIn(any())).thenReturn(
        List.of(getOrderDetail(OrderStatus.DELIVERED), getOrderDetail(OrderStatus.OUT_FOR_DELIVERY)));
    when(orderDetailRepository.findAll()).thenReturn(
        List.of(getOrderDetail(OrderStatus.DELIVERED), getOrderDetail(OrderStatus.OUT_FOR_DELIVERY),
            getOrderDetail(OrderStatus.DELIVERED), getOrderDetail(OrderStatus.IN_PREPARATION),
            getOrderDetail(OrderStatus.ACCEPTED), getOrderDetail(OrderStatus.CANCELLED),
            getOrderDetail(OrderStatus.DELIVERED), getOrderDetail(OrderStatus.AWAITING_CONFIRMATION)));

    PlatformActivityDTO result = monitoringService.getPlatformActivity();
    assertNotNull(result);
  }

  private OrderDetail getOrderDetail(OrderStatus status) {
    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setOrderStatus(status);
    return orderDetail;
  }
}