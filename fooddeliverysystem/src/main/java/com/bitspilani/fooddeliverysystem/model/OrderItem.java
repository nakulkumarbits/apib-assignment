package com.bitspilani.fooddeliverysystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  private Double price;

  @Column(nullable = false)
  private Double totalPrice;

  @ManyToOne
  @JoinColumn(name = "order_detail_id", nullable = false)
  private OrderDetail orderDetail;

  @ManyToOne
  @JoinColumn(name = "menu_item_id", nullable = false)
  private MenuItem menuItem;
}
