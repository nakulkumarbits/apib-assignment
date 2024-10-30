package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.dto.DeliveryResponseDTO;
import com.bitspilani.fooddeliverysystem.model.OrderDetail;
import java.util.ArrayList;
import java.util.List;

public class DeliveryResponseConvertor {

  public static DeliveryResponseDTO toDTO(OrderDetail orderDetail) {
    DeliveryResponseDTO deliveryResponseDTO = new DeliveryResponseDTO();
    deliveryResponseDTO.setRestaurantName(orderDetail.getRestaurant().getRestaurantName());
    deliveryResponseDTO.setRestaurantAddress(AddressConvertor.toAddressDTO(orderDetail.getRestaurant().getRestaurantAddress()));
    deliveryResponseDTO.setAddress(AddressConvertor.toAddressDTO(orderDetail.getCustomer().getDeliveryAddress()));
    deliveryResponseDTO.setOrderId(orderDetail.getId());
    deliveryResponseDTO.setCustomerName(
        orderDetail.getCustomer().getFirstName() + " " + orderDetail.getCustomer().getLastName());
    deliveryResponseDTO.setCustomerMobileNo(orderDetail.getCustomer().getMobileNo());
    deliveryResponseDTO.setTotalAmount(orderDetail.getTotalAmount());
    deliveryResponseDTO.setOrderDate(orderDetail.getCreatedDate());
    deliveryResponseDTO.setOrderedItems(OrderConvertor.getOrderItemResponseDTOs(orderDetail));
    return deliveryResponseDTO;
  }

  public static List<DeliveryResponseDTO> toDTOList(List<OrderDetail> orderDetails) {
    List<DeliveryResponseDTO> deliveryResponseDTOS = new ArrayList<>();
    for (OrderDetail orderDetail : orderDetails) {
      deliveryResponseDTOS.add(toDTO(orderDetail));
    }
    return deliveryResponseDTOS;
  }
}
