package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.dto.MenuItemDTO;
import com.bitspilani.fooddeliverysystem.exceptions.ItemNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.MenuItemMismatchException;
import com.bitspilani.fooddeliverysystem.model.MenuItem;
import com.bitspilani.fooddeliverysystem.model.Restaurant;
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
    private RestaurantService restaurantService;

    @Autowired
    private JwtUtil jwtUtil;

    public MenuItemDTO addMenuItem(MenuItemDTO menuItemDTO, String token) {

        Long ownerId = jwtUtil.extractOwnerIdFromToken(token);
        Restaurant restaurant = restaurantService.getRestaurantById(ownerId);

        MenuItem menuItem = MenuItemConvertor.toEntity(menuItemDTO);
        menuItem.setRestaurant(restaurant);
        menuItem = menuItemRepository.save(menuItem);
        return MenuItemConvertor.toDTO(menuItem);
    }

    public MenuItemDTO updateMenuItem(Long id, MenuItemDTO menuItemDTO, String token) {
        if (!Objects.equals(id, menuItemDTO.getItemId())) {
            throw new MenuItemMismatchException(FoodDeliveryConstants.MENU_ITEM_MISMATCH);
        }

        Long ownerId = jwtUtil.extractOwnerIdFromToken(token);
        Restaurant restaurant = restaurantService.getRestaurantById(ownerId);

        Optional<MenuItem> menuItemOptional = menuItemRepository.findById(id);
        if (menuItemOptional.isPresent()
            && Objects.equals(menuItemOptional.get().getRestaurant().getId(), restaurant.getId())) {
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
        Long ownerId = jwtUtil.extractOwnerIdFromToken(token);
        Restaurant restaurant = restaurantService.getRestaurantById(ownerId);
        if (Objects.equals(ownerId, restaurant.getId())) {
            menuItemRepository.delete(menuItem);
        }
    }

    public List<MenuItemDTO> getMenuItemsByOwner(String token) {
        Long ownerId = jwtUtil.extractOwnerIdFromToken(token);
        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(ownerId);
        return MenuItemConvertor.toDTOList(menuItems);
    }

    private void copyFields(MenuItem menuItem, MenuItemDTO menuItemDTO) {
        menuItem.setName(menuItemDTO.getName());
        menuItem.setDescription(menuItemDTO.getDescription());
        menuItem.setPrice(menuItemDTO.getPrice());
    }
}
