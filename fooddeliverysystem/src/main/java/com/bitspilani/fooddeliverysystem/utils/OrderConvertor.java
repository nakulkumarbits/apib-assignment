package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.dto.OrderItemResponseDTO;
import com.bitspilani.fooddeliverysystem.dto.OrderResponseDTO;
import com.bitspilani.fooddeliverysystem.model.OrderDetail;
import java.util.ArrayList;
import java.util.List;

public class OrderConvertor {

  public static List<OrderResponseDTO> toDTOList(List<OrderDetail> orderDetails) {
    List<OrderResponseDTO> orderResponseDTOS = new ArrayList<>();
    for (OrderDetail orderDetail : orderDetails) {
      orderResponseDTOS.add(toDTO(orderDetail));
    }
    return orderResponseDTOS;
  }

  public static OrderResponseDTO toDTO(OrderDetail orderDetail) {
    OrderResponseDTO response = new OrderResponseDTO();
    List<OrderItemResponseDTO> orderItemResponseDTOs = getOrderItemResponseDTOs(orderDetail);
    response.setOrderId(orderDetail.getId());
    response.setCustomerName(
        orderDetail.getCustomer().getFirstName() + " " + orderDetail.getCustomer().getLastName());
    response.setCustomerMobileNo(orderDetail.getCustomer().getMobileNo());
    response.setRestaurantName(orderDetail.getRestaurant().getRestaurantName());
    response.setOrderedItems(orderItemResponseDTOs);
    response.setTotalAmount(orderDetail.getTotalAmount());
    response.setOrderStatus(orderDetail.getOrderStatus());
    response.setOrderDate(orderDetail.getCreatedDate());
    return response;
  }

  public static List<OrderItemResponseDTO> getOrderItemResponseDTOs(OrderDetail orderDetail) {
    return orderDetail.getOrderItems().stream()
        .map(orderItem -> {
          OrderItemResponseDTO itemResponse = new OrderItemResponseDTO();
          itemResponse.setItemName(orderItem.getMenuItem().getName());
          itemResponse.setQuantity(orderItem.getQuantity());
          itemResponse.setPrice(orderItem.getPrice());
          itemResponse.setTotalPrice(orderItem.getTotalPrice());
          return itemResponse;
        }).toList();
  }
}
