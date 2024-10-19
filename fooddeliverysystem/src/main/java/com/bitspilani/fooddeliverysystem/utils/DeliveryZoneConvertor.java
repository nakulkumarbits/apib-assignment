package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.dto.DeliveryZoneDTO;
import com.bitspilani.fooddeliverysystem.model.RestaurantDeliveryZone;
import java.util.ArrayList;
import java.util.List;

public class DeliveryZoneConvertor {

    public static RestaurantDeliveryZone toEntity(DeliveryZoneDTO deliveryZoneDTO) {
        RestaurantDeliveryZone restaurantDeliveryZone = new RestaurantDeliveryZone();
        restaurantDeliveryZone.setZoneName(deliveryZoneDTO.getZoneName());
        restaurantDeliveryZone.setPinCode(deliveryZoneDTO.getPinCode());
        return restaurantDeliveryZone;
    }

    public static DeliveryZoneDTO toDTO(RestaurantDeliveryZone restaurantDeliveryZone) {
        DeliveryZoneDTO deliveryZoneDTO = new DeliveryZoneDTO();
        deliveryZoneDTO.setZoneName(restaurantDeliveryZone.getZoneName());
        deliveryZoneDTO.setPinCode(restaurantDeliveryZone.getPinCode());
        return deliveryZoneDTO;
    }

    public static List<RestaurantDeliveryZone> toEntityList(List<DeliveryZoneDTO> deliveryZoneDTOS) {
        List<RestaurantDeliveryZone> restaurantDeliveryZones = new ArrayList<>();
        deliveryZoneDTOS.forEach(deliveryZoneDTO -> restaurantDeliveryZones.add(toEntity(deliveryZoneDTO)));
        return restaurantDeliveryZones;

    }

    public static List<DeliveryZoneDTO> toDTOList(List<RestaurantDeliveryZone> deliveryZones) {
        List<DeliveryZoneDTO> deliveryZoneDTOS = new ArrayList<>();
        deliveryZones.forEach(deliveryZone -> deliveryZoneDTOS.add(toDTO(deliveryZone)));
        return deliveryZoneDTOS;
    }
}
