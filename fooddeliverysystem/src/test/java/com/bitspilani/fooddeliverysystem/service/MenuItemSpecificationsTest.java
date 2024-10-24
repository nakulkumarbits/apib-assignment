package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.enums.CuisineType;
import com.bitspilani.fooddeliverysystem.enums.ItemAvailable;
import com.bitspilani.fooddeliverysystem.enums.ItemType;
import com.bitspilani.fooddeliverysystem.model.MenuItem;
import com.bitspilani.fooddeliverysystem.service.MenuItemSpecifications;
import jakarta.persistence.criteria.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class MenuItemSpecificationsTest {

    @Mock
    private Root<MenuItem> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testCuisineTypeEquals_WhenCuisineTypeIsNull() {
        // Test that null cuisineType returns a conjunction predicate (no filtering)
        Specification<MenuItem> specification = MenuItemSpecifications.cuisineTypeEquals(null);
        assertNotNull(specification);

        // Apply specification and verify criteriaBuilder.conjunction() is called
        specification.toPredicate(root, query, criteriaBuilder);
        verify(criteriaBuilder).conjunction();
    }

    @Test
     void testCuisineTypeEquals_WhenCuisineTypeIsNotNull() {
        // Test that a non-null cuisineType creates an equality predicate
        CuisineType cuisineType = CuisineType.ITALIAN;
        Specification<MenuItem> specification = MenuItemSpecifications.cuisineTypeEquals(cuisineType);
        assertNotNull(specification);

        // Mock the behavior of root.get("cuisine")
        when(root.get("cuisine")).thenReturn(mock(Path.class));

        // Apply specification and verify criteriaBuilder.equal() is called
        specification.toPredicate(root, query, criteriaBuilder);
        verify(criteriaBuilder).equal(root.get("cuisine"), cuisineType);
    }

    @Test
     void testItemTypeEquals_WhenItemTypeIsNull() {
        // Test that null itemType returns a conjunction predicate
        Specification<MenuItem> specification = MenuItemSpecifications.itemTypeEquals(null);
        assertNotNull(specification);

        // Apply specification and verify criteriaBuilder.conjunction() is called
        specification.toPredicate(root, query, criteriaBuilder);
        verify(criteriaBuilder).conjunction();
    }

    @Test
     void testItemTypeEquals_WhenItemTypeIsNotNull() {
        // Test that a non-null itemType creates an equality predicate
        ItemType itemType = ItemType.VEG;
        Specification<MenuItem> specification = MenuItemSpecifications.itemTypeEquals(itemType);
        assertNotNull(specification);

        // Mock the behavior of root.get("itemType")
        when(root.get("itemType")).thenReturn(mock(Path.class));

        // Apply specification and verify criteriaBuilder.equal() is called
        specification.toPredicate(root, query, criteriaBuilder);
        verify(criteriaBuilder).equal(root.get("itemType"), itemType);
    }

    @Test
     void testItemAvailableEquals_WhenItemAvailableIsNull() {
        // Test that null itemAvailable returns a conjunction predicate
        Specification<MenuItem> specification = MenuItemSpecifications.itemAvailableEquals(null);
        assertNotNull(specification);

        // Apply specification and verify criteriaBuilder.conjunction() is called
        specification.toPredicate(root, query, criteriaBuilder);
        verify(criteriaBuilder).conjunction();
    }

    @Test
     void testItemAvailableEquals_WhenItemAvailableIsNotNull() {
        // Test that a non-null itemAvailable creates an equality predicate
        ItemAvailable itemAvailable = ItemAvailable.YES;
        Specification<MenuItem> specification = MenuItemSpecifications.itemAvailableEquals(itemAvailable);
        assertNotNull(specification);

        // Mock the behavior of root.get("itemAvailable")
        when(root.get("itemAvailable")).thenReturn(mock(Path.class));

        // Apply specification and verify criteriaBuilder.equal() is called
        specification.toPredicate(root, query, criteriaBuilder);
        verify(criteriaBuilder).equal(root.get("itemAvailable"), itemAvailable);
    }

    @Test
     void testFetchRestaurants() {
        // Test that fetchRestaurants creates a specification with a join
        Specification<MenuItem> specification = MenuItemSpecifications.fetchRestaurants();
        assertNotNull(specification);

        // Mock the join and multiselect behavior
        Join<Object, Object> restaurantJoin = mock(Join.class);
        when(root.join("restaurant")).thenReturn(restaurantJoin);

        // Apply specification
        specification.toPredicate(root, query, criteriaBuilder);

        // Verify the join and multiselect calls
        verify(root).join("restaurant");
        verify(query).multiselect(
            restaurantJoin.get("restaurantName"),
            root.get("name"),
            root.get("cuisine"),
            root.get("itemType"),
            root.get("itemAvailable"),
            root.get("price")
        );
    }
}
