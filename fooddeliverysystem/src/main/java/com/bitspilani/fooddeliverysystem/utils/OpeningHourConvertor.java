package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.dto.OpeningHourDTO;
import com.bitspilani.fooddeliverysystem.model.RestaurantOpeningDetail;
import java.util.ArrayList;
import java.util.List;

public class OpeningHourConvertor {

    public static RestaurantOpeningDetail toEntity(OpeningHourDTO openingHourDTO) {
        RestaurantOpeningDetail restaurantOpeningDetail = new RestaurantOpeningDetail();
        restaurantOpeningDetail.setOpeningTime(openingHourDTO.getOpeningTime());
        restaurantOpeningDetail.setClosingTime(openingHourDTO.getClosingTime());
        restaurantOpeningDetail.setDay(openingHourDTO.getDay());
        return restaurantOpeningDetail;
    }

    public static OpeningHourDTO toDTO(RestaurantOpeningDetail restaurantOpeningDetail) {
        OpeningHourDTO openingHourDTO = new OpeningHourDTO();
        openingHourDTO.setOpeningTime(restaurantOpeningDetail.getOpeningTime());
        openingHourDTO.setClosingTime(restaurantOpeningDetail.getClosingTime());
        openingHourDTO.setDay(restaurantOpeningDetail.getDay());
        return openingHourDTO;
    }

    public static List<RestaurantOpeningDetail> toEntityList(List<OpeningHourDTO> openingHourDTOList) {
        List<RestaurantOpeningDetail> restaurantOpeningDetails = new ArrayList<>();
        openingHourDTOList.forEach(openingHourDTO -> restaurantOpeningDetails.add(toEntity(openingHourDTO)));
        return restaurantOpeningDetails;
    }

    public static List<OpeningHourDTO> toDTOList(List<RestaurantOpeningDetail> openingHourDTOList) {
        List<OpeningHourDTO> openingHourDTOS = new ArrayList<>();
        openingHourDTOList.forEach(openingHourDTO -> openingHourDTOS.add(toDTO(openingHourDTO)));
        return openingHourDTOS;
    }
}
