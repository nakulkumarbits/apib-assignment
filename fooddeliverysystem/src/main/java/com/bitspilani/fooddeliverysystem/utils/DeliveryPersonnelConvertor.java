package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.model.DeliveryPersonnel;
import com.bitspilani.fooddeliverysystem.model.User;
import java.util.ArrayList;
import java.util.List;

public class DeliveryPersonnelConvertor {

    public static DeliveryPersonnelDTO toDeliveryPersonnelDTO(DeliveryPersonnel deliveryPersonnel) {
        DeliveryPersonnelDTO deliveryPersonnelDTO = new DeliveryPersonnelDTO();
        deliveryPersonnelDTO.setUsername(deliveryPersonnel.getUser().getUsername());
        deliveryPersonnelDTO.setVehicleType(deliveryPersonnel.getVehicleType());
        deliveryPersonnelDTO.setAddress(AddressConvertor.toAddressDTO(deliveryPersonnel.getVehicleAddress()));
        return deliveryPersonnelDTO;
    }

    public static DeliveryPersonnel toDeliveryPersonnel(DeliveryPersonnelDTO deliveryPersonnelDTO) {
        DeliveryPersonnel deliveryPersonnel = new DeliveryPersonnel();
        deliveryPersonnel.setVehicleType(deliveryPersonnelDTO.getVehicleType());
        deliveryPersonnel.setVehicleAddress(AddressConvertor.toAddress(deliveryPersonnelDTO.getAddress()));

        User user = new User();
        user.setUsername(deliveryPersonnelDTO.getUsername());
        user.setPassword(deliveryPersonnelDTO.getPassword());
        user.setRole(UserRole.DELIVERY_PERSONNEL);
        deliveryPersonnel.setUser(user);
        return deliveryPersonnel;
    }

    public static List<DeliveryPersonnelDTO> toDeliveryPersonnelList(List<DeliveryPersonnel> deliveryPersonnels) {
        List<DeliveryPersonnelDTO> deliveryPersonnelDTOS = new ArrayList<>();
        deliveryPersonnels.forEach(deliveryPersonnel -> deliveryPersonnelDTOS.add(toDeliveryPersonnelDTO(deliveryPersonnel)));
        return deliveryPersonnelDTOS;
    }
}
