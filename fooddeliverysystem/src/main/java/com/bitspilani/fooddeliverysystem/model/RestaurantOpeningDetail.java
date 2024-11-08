package com.bitspilani.fooddeliverysystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class RestaurantOpeningDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_owner_id", nullable = false)
    private Restaurant restaurant; // Link to the restaurant owner


    private String day; // e.g., "Monday", "Tuesday"
    private String openingTime = "10:00 AM"; // e.g., "09:00 AM"
    private String closingTime = "10:00 PM"; // e.g., "10:00 PM"
}
