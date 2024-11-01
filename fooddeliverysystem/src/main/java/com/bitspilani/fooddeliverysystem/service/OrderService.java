package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.DeliveryResponseDTO;
import com.bitspilani.fooddeliverysystem.dto.OrderRequestDTO;
import com.bitspilani.fooddeliverysystem.dto.OrderResponseDTO;
import com.bitspilani.fooddeliverysystem.enums.OrderStatus;
import com.bitspilani.fooddeliverysystem.enums.PaymentStatus;
import com.bitspilani.fooddeliverysystem.exceptions.InvalidRequestException;
import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.model.MenuItem;
import com.bitspilani.fooddeliverysystem.model.OrderDetail;
import com.bitspilani.fooddeliverysystem.model.OrderItem;
import com.bitspilani.fooddeliverysystem.model.OrderPaymentDetail;
import com.bitspilani.fooddeliverysystem.model.Restaurant;
import com.bitspilani.fooddeliverysystem.repository.CustomerRepository;
import com.bitspilani.fooddeliverysystem.repository.MenuItemRepository;
import com.bitspilani.fooddeliverysystem.repository.OrderDetailRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantRepository;
import com.bitspilani.fooddeliverysystem.security.JwtUtil;
import com.bitspilani.fooddeliverysystem.utils.DeliveryResponseConvertor;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import com.bitspilani.fooddeliverysystem.utils.OrderConvertor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

  private final OrderDetailRepository orderDetailRepository;
  private final CustomerRepository customerRepository;
  private final MenuItemRepository menuItemRepository;
  private final RestaurantRepository restaurantRepository;
  private final JwtUtil jwtUtil;

  public OrderService(OrderDetailRepository orderDetailRepository, CustomerRepository customerRepository,
      MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository,
      JwtUtil jwtUtil) {
    this.orderDetailRepository = orderDetailRepository;
    this.customerRepository = customerRepository;
    this.menuItemRepository = menuItemRepository;
    this.restaurantRepository = restaurantRepository;
    this.jwtUtil = jwtUtil;
  }

  @Transactional
  public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO, String token) {
    Long customerIdFromToken = jwtUtil.extractCustomerIdFromToken(token);
    if (!Objects.equals(customerIdFromToken, orderRequestDTO.getCustomerId())) {
      throw new InvalidRequestException(FoodDeliveryConstants.CUSTOMER_ID_INCORRECT);
    }
    Customer customer = customerRepository.findById(customerIdFromToken)
        .orElseThrow(() -> new InvalidRequestException(FoodDeliveryConstants.CUSTOMER_ID_INCORRECT));

    Restaurant restaurant = restaurantRepository.findById(orderRequestDTO.getRestaurantId())
        .orElseThrow(() -> new InvalidRequestException(FoodDeliveryConstants.RESTAURANT_ID_INCORRECT));

    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setCustomer(customer);
    orderDetail.setRestaurant(restaurant);
    orderDetail.setOrderStatus(OrderStatus.AWAITING_CONFIRMATION);
    orderDetail.setPaymentMethod(orderRequestDTO.getPaymentMethod());

    // Calculate total amount and create order items
    List<OrderItem> orderItems = orderRequestDTO.getOrderItems().stream()
        .map(itemRequest -> {
          MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
              .orElseThrow(() -> new IllegalArgumentException("Invalid menu item ID"));
          OrderItem orderItem = new OrderItem();
          orderItem.setMenuItem(menuItem);
          orderItem.setQuantity(itemRequest.getQuantity());
          orderItem.setPrice(menuItem.getPrice());
          orderItem.setTotalPrice(menuItem.getPrice() * itemRequest.getQuantity());
          orderItem.setOrderDetail(orderDetail); // Link to order
          return orderItem;
        }).toList();

    // Set total amount and order items to orderDetail
    Double totalAmount = orderItems.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    orderDetail.setTotalAmount(totalAmount);
    orderDetail.setOrderItems(orderItems);

    OrderPaymentDetail orderPaymentDetail = new OrderPaymentDetail();
    orderPaymentDetail.setAmount(totalAmount);
    orderPaymentDetail.setPaymentMethod(orderRequestDTO.getPaymentMethod());
    orderPaymentDetail.setPaymentStatus(PaymentStatus.COMPLETED);

    orderDetail.setOrderPaymentDetail(orderPaymentDetail);

    // Save the order
    OrderDetail savedOrder = orderDetailRepository.save(orderDetail);
    return OrderConvertor.toDTO(savedOrder);
  }

  public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus, String token) {
    OrderDetail orderDetail = orderDetailRepository.findById(orderId)
        .orElseThrow(() -> new InvalidRequestException(FoodDeliveryConstants.ORDER_NOT_FOUND));

    if (Objects.equals(orderDetail.getOrderStatus(), newStatus)) {
      throw new InvalidRequestException(FoodDeliveryConstants.ORDER_SAME_STATUS_ERROR);
    }

    if (jwtUtil.extractOwnerIdFromToken(token) != 0
        && !FoodDeliveryConstants.RESTAURANT_ALLOWED_ORDER_STATUSES.contains(newStatus)) {
      throw new InvalidRequestException(FoodDeliveryConstants.RESTAURANT_STATUS_ERROR + newStatus);
    } else if (jwtUtil.extractDeliveryPersonnelIdFromToken(token) != 0
        && !FoodDeliveryConstants.DELIVERY_ALLOWED_ORDER_STATUSES.contains(newStatus)) {
      throw new InvalidRequestException(FoodDeliveryConstants.DELIVERY_STATUS_ERROR + newStatus);
    }

    orderDetail.setOrderStatus(newStatus);
    orderDetailRepository.save(orderDetail);

    // Return response DTO
    OrderResponseDTO orderResponseDTO = OrderConvertor.toDTO(orderDetail);
    orderResponseDTO.setOrderStatus(newStatus);
    return orderResponseDTO;
  }

  public OrderResponseDTO getOrder(Long orderId) {
    OrderDetail orderDetail = orderDetailRepository.findById(orderId)
        .orElseThrow(() -> new InvalidRequestException(FoodDeliveryConstants.ORDER_NOT_FOUND));
    // Return response DTO
    return OrderConvertor.toDTO(orderDetail);
  }

  public List<OrderResponseDTO> getOrders(String token) {
    Long customerIdFromToken = jwtUtil.extractCustomerIdFromToken(token);
    List<OrderDetail> orderDetails = orderDetailRepository.findByCustomerIdOrderByCreatedDateDesc(customerIdFromToken);

    return OrderConvertor.toDTOList(orderDetails);
  }

  public List<OrderResponseDTO> getIncomingOrders(String token) {
    List<OrderDetail> orderDetails;
    if (jwtUtil.hasAdminRole(token)) {
      orderDetails = orderDetailRepository.findByOrderStatusIn(
          FoodDeliveryConstants.RESTAURANT_ORDER_STATUSES);
    } else {
      Long ownerIdFromToken = jwtUtil.extractOwnerIdFromToken(token);
      orderDetails = orderDetailRepository.findByRestaurantIdAndOrderStatusIn(
          ownerIdFromToken, FoodDeliveryConstants.RESTAURANT_ORDER_STATUSES);
    }
    return OrderConvertor.toDTOList(orderDetails);
  }

  public List<DeliveryResponseDTO> getDeliverables(String token) {
    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderStatusIn(
        FoodDeliveryConstants.DELIVERY_ORDER_STATUSES);

    return DeliveryResponseConvertor.toDTOList(orderDetails);
  }
}
