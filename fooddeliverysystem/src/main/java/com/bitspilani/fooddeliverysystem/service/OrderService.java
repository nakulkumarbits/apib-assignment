package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.OrderItemResponseDTO;
import com.bitspilani.fooddeliverysystem.dto.OrderRequestDTO;
import com.bitspilani.fooddeliverysystem.dto.OrderResponseDTO;
import com.bitspilani.fooddeliverysystem.enums.OrderStatus;
import com.bitspilani.fooddeliverysystem.exceptions.InvalidRequestException;
import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.model.MenuItem;
import com.bitspilani.fooddeliverysystem.model.OrderDetail;
import com.bitspilani.fooddeliverysystem.model.OrderItem;
import com.bitspilani.fooddeliverysystem.model.Restaurant;
import com.bitspilani.fooddeliverysystem.repository.CustomerRepository;
import com.bitspilani.fooddeliverysystem.repository.MenuItemRepository;
import com.bitspilani.fooddeliverysystem.repository.OrderDetailRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantRepository;
import com.bitspilani.fooddeliverysystem.security.JwtUtil;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
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

    // Save the order
    OrderDetail savedOrder = orderDetailRepository.save(orderDetail);

    // Prepare response DTO
    List<OrderItemResponseDTO> orderItemResponseDTOs = savedOrder.getOrderItems().stream()
        .map(orderItem -> {
          OrderItemResponseDTO itemResponse = new OrderItemResponseDTO();
          itemResponse.setItemName(orderItem.getMenuItem().getName());
          itemResponse.setQuantity(orderItem.getQuantity());
          itemResponse.setPrice(orderItem.getPrice());
          itemResponse.setTotalPrice(orderItem.getTotalPrice());
          return itemResponse;
        }).toList();

    // Return response DTO
    OrderResponseDTO response = new OrderResponseDTO();
    response.setOrderId(savedOrder.getId());
    response.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
    response.setRestaurantName(restaurant.getRestaurantName());
    response.setOrderedItems(orderItemResponseDTOs);
    response.setTotalAmount(savedOrder.getTotalAmount());
    response.setOrderStatus(savedOrder.getOrderStatus());
    response.setOrderDate(savedOrder.getCreatedDate());

    return response;
  }

  public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus) {
    OrderDetail orderDetail = orderDetailRepository.findById(orderId)
        .orElseThrow(() -> new InvalidRequestException("Order not found"));

    orderDetail.setOrderStatus(newStatus);
    orderDetailRepository.save(orderDetail);

    // Prepare response DTO
    List<OrderItemResponseDTO> orderItemResponseDTOs = orderDetail.getOrderItems().stream()
        .map(orderItem -> {
          OrderItemResponseDTO itemResponse = new OrderItemResponseDTO();
          itemResponse.setItemName(orderItem.getMenuItem().getName());
          itemResponse.setQuantity(orderItem.getQuantity());
          itemResponse.setPrice(orderItem.getPrice());
          itemResponse.setTotalPrice(orderItem.getTotalPrice());
          return itemResponse;
        }).toList();

    // Return response DTO
    OrderResponseDTO response = new OrderResponseDTO();
    response.setOrderId(orderId);
    response.setCustomerName(orderDetail.getCustomer().getFirstName() + " " + orderDetail.getCustomer().getLastName());
    response.setRestaurantName(orderDetail.getRestaurant().getRestaurantName());
    response.setOrderedItems(orderItemResponseDTOs);
    response.setTotalAmount(orderDetail.getTotalAmount());
    response.setOrderStatus(newStatus);
    response.setOrderDate(orderDetail.getCreatedDate());
    return response;
  }

  public OrderResponseDTO getOrder(Long orderId) {
    OrderDetail orderDetail = orderDetailRepository.findById(orderId)
        .orElseThrow(() -> new InvalidRequestException("Order not found"));
    // Prepare response DTO
    List<OrderItemResponseDTO> orderItemResponseDTOs = orderDetail.getOrderItems().stream()
        .map(orderItem -> {
          OrderItemResponseDTO itemResponse = new OrderItemResponseDTO();
          itemResponse.setItemName(orderItem.getMenuItem().getName());
          itemResponse.setQuantity(orderItem.getQuantity());
          itemResponse.setPrice(orderItem.getPrice());
          itemResponse.setTotalPrice(orderItem.getTotalPrice());
          return itemResponse;
        }).toList();

    // Return response DTO
    OrderResponseDTO response = new OrderResponseDTO();
    response.setOrderId(orderId);
    response.setCustomerName(orderDetail.getCustomer().getFirstName() + " " + orderDetail.getCustomer().getLastName());
    response.setRestaurantName(orderDetail.getRestaurant().getRestaurantName());
    response.setOrderedItems(orderItemResponseDTOs);
    response.setTotalAmount(orderDetail.getTotalAmount());
    response.setOrderStatus(orderDetail.getOrderStatus());
    response.setOrderDate(orderDetail.getCreatedDate());
    return response;
  }
}
