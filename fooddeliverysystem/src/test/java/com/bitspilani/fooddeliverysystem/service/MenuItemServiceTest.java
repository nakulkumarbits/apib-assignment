package com.bitspilani.fooddeliverysystem.service;

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
import com.bitspilani.fooddeliverysystem.model.RestaurantOwner;
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
    RestaurantOwnerService restaurantOwnerService;
    @Mock
    JwtUtil jwtUtil;
    @InjectMocks
    MenuItemService menuItemService;

    private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1JFU1RBVVJBTlRfT1dORVIiXSwib3duZXJJZCI6NSwic3ViIjoicmVzMTAiLCJpYXQiOjE3MjkzMjU4NjgsImV4cCI6MTcyOTM2MTg2OH0.g5gkrYT0_oYSjD_KBNfVJr-8_6imUcGVhlHaPBuwdJQ";
    private final long OWNER_ID = 5L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtUtil.extractOwnerId(anyString())).thenReturn(OWNER_ID);
    }

    @Test
    void testAddMenuItem() {
        when(menuItemRepository.save(any())).thenReturn(new MenuItem());
        when(restaurantOwnerService.getRestaurantById(anyLong())).thenReturn(new RestaurantOwner());

        MenuItemDTO result = menuItemService.addMenuItem(new MenuItemDTO(), TOKEN);
        assertEquals(new MenuItemDTO(), result);
    }

    @Test
    void testUpdateMenuItem() {
        RestaurantOwner restaurantOwner = new RestaurantOwner();
        restaurantOwner.setId(1L);
        MenuItem menuItem = new MenuItem();
        menuItem.setRestaurantOwner(restaurantOwner);
        when(menuItemRepository.findById(any())).thenReturn(Optional.of(menuItem));
        when(restaurantOwnerService.getRestaurantById(anyLong())).thenReturn(restaurantOwner);
        when(menuItemRepository.save(any())).thenReturn(menuItem);
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId(1L);
        MenuItemDTO result = menuItemService.updateMenuItem(Long.valueOf(1), menuItemDTO, TOKEN);
        assertNotNull(result);
    }

    @Test
    void testUpdateMenuItemThrowsMenuItemMismatchException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId(10L);
        MenuItemMismatchException exception = assertThrows(MenuItemMismatchException.class,
            () -> menuItemService.updateMenuItem(1L, menuItemDTO, TOKEN));
        assertEquals(FoodDeliveryConstants.MENU_ITEM_MISMATCH, exception.getMessage());
    }

    @Test
    void testUpdateMenuItemWhenItemNotPresent() {
        when(menuItemRepository.findById(any())).thenReturn(Optional.empty());
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId(10L);
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
            () -> menuItemService.updateMenuItem(10L, menuItemDTO, TOKEN));
        assertEquals(FoodDeliveryConstants.MENU_ITEM_NOT_PRESENT, exception.getMessage());
    }

    @Test
    void testRemoveMenuItem() {
        RestaurantOwner restaurantOwner = new RestaurantOwner();
        restaurantOwner.setId(OWNER_ID);
        when(menuItemRepository.findById(any())).thenReturn(Optional.of(new MenuItem()));
        when(restaurantOwnerService.getRestaurantById(anyLong())).thenReturn(restaurantOwner);

        menuItemService.removeMenuItem(1L, TOKEN);
        verify(menuItemRepository).delete(any());
    }

    @Test
    void testRemoveMenuItemWhenItemNotPresent() {
        when(menuItemRepository.findById(any())).thenReturn(Optional.empty());
        ItemNotFoundException exception =
            assertThrows(ItemNotFoundException.class, () -> menuItemService.removeMenuItem(1L, TOKEN));
        assertEquals(FoodDeliveryConstants.MENU_ITEM_NOT_PRESENT, exception.getMessage());
    }

    @Test
    void testGetMenuItemsByOwner() {
        when(menuItemRepository.findByRestaurantOwnerId(anyLong())).thenReturn(List.of(new MenuItem()));

        List<MenuItemDTO> result = menuItemService.getMenuItemsByOwner(TOKEN);
        assertEquals(List.of(new MenuItemDTO()), result);
    }
}