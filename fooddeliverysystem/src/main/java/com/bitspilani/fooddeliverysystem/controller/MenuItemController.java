package com.bitspilani.fooddeliverysystem.controller;

import com.bitspilani.fooddeliverysystem.dto.MenuItemDTO;
import com.bitspilani.fooddeliverysystem.dto.ValidationErrorResponse;
import com.bitspilani.fooddeliverysystem.service.MenuItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
@Validated
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @Operation(summary = "Add a menu item and associate to the logged in restaurant owner.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Menu item added.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuItemDTO.class))),
        @ApiResponse(responseCode = "400", description = "The error can be any of those: the provided input is not supported. " +
            "See the API spec for further details.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized."),
    })
    @PostMapping
    public ResponseEntity<MenuItemDTO> addMenuItem(@RequestBody @Valid MenuItemDTO menuItemDTO, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(menuItemService.addMenuItem(menuItemDTO, token));
    }

    @Operation(summary = "Updates a menu item for the logged in restaurant owner.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Menu item updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuItemDTO.class))),
        @ApiResponse(responseCode = "400", description = "The error can be any of those: the provided input is not supported. " +
            "See the API spec for further details.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized."),
    })
    @PutMapping("/{id}")
    public ResponseEntity<MenuItemDTO> updateMenuItem(@PathVariable("id") Long id,
        @RequestBody @Valid MenuItemDTO menuItemDTO,
        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(id, menuItemDTO, token));
    }

    @Operation(summary = "Deletes a menu item for the logged in restaurant owner.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Menu item deleted."),
        @ApiResponse(responseCode = "401", description = "Unauthorized."),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMenuItem(@PathVariable("id") Long id,
        @RequestHeader("Authorization") String token) {
        menuItemService.removeMenuItem(id, token);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Fetches menu items for a restaurant from the Food Delivery System.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Menu items were successfully fetched.", content =
        @Content(mediaType = "application/json", schema = @Schema(implementation = MenuItemDTO.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/items")
    public ResponseEntity<List<MenuItemDTO>> getMenuItemsByOwner(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByOwner(token));
    }
}
