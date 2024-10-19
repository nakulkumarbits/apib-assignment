package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.MenuItemDTO;
import com.bitspilani.fooddeliverysystem.exceptions.ItemNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.MenuItemMismatchException;
import com.bitspilani.fooddeliverysystem.model.MenuItem;
import com.bitspilani.fooddeliverysystem.model.RestaurantOwner;
import com.bitspilani.fooddeliverysystem.repository.MenuItemRepository;
import com.bitspilani.fooddeliverysystem.security.JwtUtil;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import com.bitspilani.fooddeliverysystem.utils.MenuItemConvertor;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    @Autowired
    private JwtUtil jwtUtil;

    public MenuItemDTO addMenuItem(MenuItemDTO menuItemDTO, String token) {

        Long ownerId = jwtUtil.extractOwnerId(token.substring(7));
        RestaurantOwner restaurantOwner = restaurantOwnerService.getRestaurantById(ownerId);

        MenuItem menuItem = MenuItemConvertor.toEntity(menuItemDTO);
        menuItem.setRestaurantOwner(restaurantOwner);
        menuItem = menuItemRepository.save(menuItem);
        return MenuItemConvertor.toDTO(menuItem);
    }

    public MenuItemDTO updateMenuItem(Long id, MenuItemDTO menuItemDTO, String token) {
        if (!Objects.equals(id, menuItemDTO.getItemId())) {
            throw new MenuItemMismatchException(FoodDeliveryConstants.MENU_ITEM_MISMATCH);
        }

        Long ownerId = jwtUtil.extractOwnerId(token.substring(7));
        RestaurantOwner restaurantOwner = restaurantOwnerService.getRestaurantById(ownerId);

        Optional<MenuItem> menuItemOptional = menuItemRepository.findById(id);
        if (menuItemOptional.isPresent()
            && Objects.equals(menuItemOptional.get().getRestaurantOwner().getId(), restaurantOwner.getId())) {
            MenuItem menuItem = menuItemOptional.get();
            copyFields(menuItem, menuItemDTO);
            menuItem = menuItemRepository.save(menuItem);
            return MenuItemConvertor.toDTO(menuItem);
        }
        throw new ItemNotFoundException(FoodDeliveryConstants.MENU_ITEM_NOT_PRESENT);
    }

    public void removeMenuItem(Long id, String token) {

        Optional<MenuItem> menuItemOptional = menuItemRepository.findById(id);
        if (menuItemOptional.isEmpty()) {
            throw new ItemNotFoundException(FoodDeliveryConstants.MENU_ITEM_NOT_PRESENT);
        }

        MenuItem menuItem = menuItemOptional.get();
        Long ownerId = jwtUtil.extractOwnerId(token.substring(7));
        RestaurantOwner restaurantOwner = restaurantOwnerService.getRestaurantById(ownerId);
        if (Objects.equals(ownerId, restaurantOwner.getId())) {
            menuItemRepository.delete(menuItem);
        }
    }

    public List<MenuItemDTO> getMenuItemsByOwner(String token) {
        Long ownerId = jwtUtil.extractOwnerId(token.substring(7));
        List<MenuItem> menuItems = menuItemRepository.findByRestaurantOwnerId(ownerId);
        return MenuItemConvertor.toDTOList(menuItems);
    }

    private void copyFields(MenuItem menuItem, MenuItemDTO menuItemDTO) {
        menuItem.setName(menuItemDTO.getName());
        menuItem.setDescription(menuItemDTO.getDescription());
        menuItem.setPrice(menuItemDTO.getPrice());
    }
}
