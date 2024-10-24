package com.bitspilani.fooddeliverysystem.service;

import static com.bitspilani.fooddeliverysystem.utils.FoodDeliveryTestConstants.RESTAURANT_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.MenuItemDTO;
import com.bitspilani.fooddeliverysystem.exceptions.ItemNotFoundException;
import com.bitspilani.fooddeliverysystem.exceptions.MenuItemMismatchException;
import com.bitspilani.fooddeliverysystem.model.MenuItem;
import com.bitspilani.fooddeliverysystem.model.Restaurant;
import com.bitspilani.fooddeliverysystem.repository.MenuItemRepository;
import com.bitspilani.fooddeliverysystem.security.JwtUtil;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MenuItemServiceTest {

    @Mock
    MenuItemRepository menuItemRepository;
    @Mock
    RestaurantService restaurantService;
    @Mock
    JwtUtil jwtUtil;
    @InjectMocks
    MenuItemService menuItemService;

    private final long OWNER_ID = 5L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtUtil.extractOwnerIdFromToken(anyString())).thenReturn(OWNER_ID);
    }

    @Test
    void testAddMenuItem() {
        when(menuItemRepository.save(any())).thenReturn(new MenuItem());
        when(restaurantService.getRestaurantById(anyLong())).thenReturn(new Restaurant());

        MenuItemDTO result = menuItemService.addMenuItem(new MenuItemDTO(), RESTAURANT_TOKEN);
        assertEquals(new MenuItemDTO(), result);
    }

    @Test
    void testUpdateMenuItem() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        MenuItem menuItem = new MenuItem();
        menuItem.setRestaurant(restaurant);
        when(menuItemRepository.findById(any())).thenReturn(Optional.of(menuItem));
        when(restaurantService.getRestaurantById(anyLong())).thenReturn(restaurant);
        when(menuItemRepository.save(any())).thenReturn(menuItem);
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId(1L);
        MenuItemDTO result = menuItemService.updateMenuItem(Long.valueOf(1), menuItemDTO, RESTAURANT_TOKEN);
        assertNotNull(result);
    }

    @Test
    void testUpdateMenuItemThrowsMenuItemMismatchException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId(10L);
        MenuItemMismatchException exception = assertThrows(MenuItemMismatchException.class,
            () -> menuItemService.updateMenuItem(1L, menuItemDTO, RESTAURANT_TOKEN));
        assertEquals(FoodDeliveryConstants.MENU_ITEM_MISMATCH, exception.getMessage());
    }

    @Test
    void testUpdateMenuItemWhenItemNotPresent() {
        when(menuItemRepository.findById(any())).thenReturn(Optional.empty());
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId(10L);
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
            () -> menuItemService.updateMenuItem(10L, menuItemDTO, RESTAURANT_TOKEN));
        assertEquals(FoodDeliveryConstants.MENU_ITEM_NOT_PRESENT, exception.getMessage());
    }

    @Test
    void testRemoveMenuItem() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(OWNER_ID);
        when(menuItemRepository.findById(any())).thenReturn(Optional.of(new MenuItem()));
        when(restaurantService.getRestaurantById(anyLong())).thenReturn(restaurant);

        menuItemService.removeMenuItem(1L, RESTAURANT_TOKEN);
        verify(menuItemRepository).delete(any(MenuItem.class));
    }

    @Test
    void testRemoveMenuItemWhenItemNotPresent() {
        when(menuItemRepository.findById(any())).thenReturn(Optional.empty());
        ItemNotFoundException exception =
            assertThrows(ItemNotFoundException.class, () -> menuItemService.removeMenuItem(1L, RESTAURANT_TOKEN));
        assertEquals(FoodDeliveryConstants.MENU_ITEM_NOT_PRESENT, exception.getMessage());
    }

    @Test
    void testGetMenuItemsByOwner() {
        when(menuItemRepository.findByRestaurantId(anyLong())).thenReturn(List.of(new MenuItem()));

        List<MenuItemDTO> result = menuItemService.getMenuItemsByOwner(RESTAURANT_TOKEN);
        assertEquals(List.of(new MenuItemDTO()), result);
    }
}