package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.enums.CuisineType;
import com.bitspilani.fooddeliverysystem.enums.ItemAvailable;
import com.bitspilani.fooddeliverysystem.enums.ItemType;
import com.bitspilani.fooddeliverysystem.model.MenuItem;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class MenuItemSpecifications {

  public static Specification<MenuItem> cuisineTypeEquals(CuisineType cuisineType) {
    return (root, query, criteriaBuilder) -> cuisineType == null ?
        criteriaBuilder.conjunction() :
        criteriaBuilder.equal(root.get("cuisine"), cuisineType);
  }

  public static Specification<MenuItem> itemTypeEquals(ItemType itemType) {
    return (root, query, criteriaBuilder) -> itemType == null ?
        criteriaBuilder.conjunction() :
        criteriaBuilder.equal(root.get("itemType"), itemType);
  }

  public static Specification<MenuItem> itemAvailableEquals(ItemAvailable itemAvailable) {
    return (root, query, criteriaBuilder) -> itemAvailable == null ?
        criteriaBuilder.conjunction() :
        criteriaBuilder.equal(root.get("itemAvailable"), itemAvailable);
  }

  public static Specification<MenuItem> fetchRestaurants() {
    return (root, query, criteriaBuilder) -> {
      Join<Object, Object> restaurantJoin = root.join("restaurant");
      query.multiselect(restaurantJoin.get("restaurantName"), root.get("name"), root.get("cuisine"),
          root.get("itemType"), root.get("itemAvailable"), root.get("price"));
      return criteriaBuilder.conjunction();
    };
  }
}
