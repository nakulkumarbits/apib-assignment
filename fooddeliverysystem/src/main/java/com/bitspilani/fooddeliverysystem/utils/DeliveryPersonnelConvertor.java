package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.dto.DeliveryPersonnelDTO;
import com.bitspilani.fooddeliverysystem.model.DeliveryPersonnel;

public class DeliveryPersonnelConvertor {

    public static DeliveryPersonnelDTO toDeliveryPersonnelDTO(DeliveryPersonnel deliveryPersonnel) {
        DeliveryPersonnelDTO deliveryPersonnelDTO = new DeliveryPersonnelDTO();
        deliveryPersonnelDTO.setUsername(deliveryPersonnel.getUsername());
//        deliveryPersonnelDTO.setPassword(deliveryPersonnel.getPassword());
        deliveryPersonnelDTO.setVehicleType(deliveryPersonnel.getVehicleType());
        deliveryPersonnelDTO.setAddress(AddressConvertor.toAddressDTO(deliveryPersonnel.getVehicleAddress()));
        return deliveryPersonnelDTO;
    }

    public static DeliveryPersonnel toDeliveryPersonnel(DeliveryPersonnelDTO deliveryPersonnelDTO) {
        DeliveryPersonnel deliveryPersonnel = new DeliveryPersonnel();
        deliveryPersonnel.setUsername(deliveryPersonnelDTO.getUsername());
        deliveryPersonnel.setPassword(deliveryPersonnelDTO.getPassword());
        deliveryPersonnel.setVehicleType(deliveryPersonnelDTO.getVehicleType());
        deliveryPersonnel.setVehicleAddress(AddressConvertor.toAddress(deliveryPersonnelDTO.getAddress()));
        return deliveryPersonnel;
    }
}
