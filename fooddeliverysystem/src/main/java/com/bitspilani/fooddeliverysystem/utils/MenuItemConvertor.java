package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.dto.MenuItemDTO;
import com.bitspilani.fooddeliverysystem.model.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class MenuItemConvertor {

    public static MenuItem toEntity(MenuItemDTO menuItemDTO) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDTO.getName());
        menuItem.setDescription(menuItemDTO.getDescription());
        menuItem.setPrice(menuItemDTO.getPrice());
        menuItem.setItemAvailable(menuItemDTO.getItemAvailable());
        menuItem.setItemType(menuItemDTO.getItemType());
        menuItem.setCuisine(menuItemDTO.getCuisine());
        return menuItem;
    }

    public static MenuItemDTO toDTO(MenuItem menuItem) {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId(menuItemDTO.getItemId());
        menuItemDTO.setName(menuItem.getName());
        menuItemDTO.setDescription(menuItem.getDescription());
        menuItemDTO.setPrice(menuItem.getPrice());
        menuItemDTO.setItemAvailable(menuItem.getItemAvailable());
        menuItemDTO.setItemType(menuItem.getItemType());
        menuItemDTO.setCuisine(menuItem.getCuisine());
        return menuItemDTO;
    }

    public static List<MenuItemDTO> toDTOList(List<MenuItem> menuItems) {
        List<MenuItemDTO> menuItemDTOList = new ArrayList<>();
        menuItems.forEach(menuItem -> menuItemDTOList.add(toDTO(menuItem)));
        return menuItemDTOList;
    }
}
