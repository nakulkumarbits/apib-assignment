package com.bitspilani.fooddeliverysystem.utils;

import com.bitspilani.fooddeliverysystem.enums.OrderStatus;
import java.util.List;

public interface FoodDeliveryConstants {

  String DELIVERY_PERSONNEL_NOT_PRESENT = "Delivery personnel does not exist.";
  String CUSTOMER_NOT_PRESENT = "Customer does not exist.";
  String RESTAURANT_NOT_PRESENT = "Restaurant does not exist.";
  String USERNAME_MISMATCH = "Username mismatch in request and body.";
  String MENU_ITEM_MISMATCH = "Menu item mismatch in request and body.";
  String USER_NOT_PRESENT = "User not found";
  String MENU_ITEM_NOT_PRESENT = "Menu item not found";
  String CUSTOMER_ID_INCORRECT = "Customer ID incorrect";
  String RESTAURANT_ID_INCORRECT = "Restaurant ID incorrect";
  String ORDER_NOT_FOUND = "Order not found";
  String ORDER_SAME_STATUS_ERROR = "Order status cannot be changed to same status";
  String RESTAURANT_STATUS_ERROR = "Restaurant not allowed to update status to : ";
  String DELIVERY_STATUS_ERROR = "Delivery personnel not allowed to update status to : ";
  String PERIOD_ERROR = "Invalid period: ";

  String MOST_POPULAR_RESTAURANTS = "Most Popular Restaurants";
  String RESTAURANT_ORDERS = "Restaurant Orders";
  String AVERAGE_DELIVERY_TIME = "Average Delivery Time";
  String AVERAGE_DELIVERY_TIME_MINUTES = "Average Delivery Time (minutes)";
  String DAILY = "daily";
  String WEEKLY = "weekly";
  String MONTHLY = "monthly";
  String ORDER_TRENDS = "Order Trends";

  String ROLE_ADMIN = "ROLE_ADMIN";
  String ROLE_CUSTOMER = "ROLE_CUSTOMER";
  String ROLE_RESTAURANT_OWNER = "ROLE_RESTAURANT_OWNER";
  String ROLE_DELIVERY_PERSONNEL = "ROLE_DELIVERY_PERSONNEL";

  String LOGOUT_MSG = "Logout successful";
  String TOKEN_LOGOUT_MSG = "Token has been logged out";
  String AUTHORIZATION ="Authorization";
  String BEARER = "Bearer ";

  List<OrderStatus> RESTAURANT_ORDER_STATUSES = List.of(OrderStatus.AWAITING_CONFIRMATION, OrderStatus.ACCEPTED,
      OrderStatus.IN_PREPARATION, OrderStatus.READY_FOR_DELIVERY);

  List<OrderStatus> DELIVERY_ORDER_STATUSES = List.of(OrderStatus.READY_FOR_DELIVERY, OrderStatus.OUT_FOR_DELIVERY);

  List<OrderStatus> RESTAURANT_ALLOWED_ORDER_STATUSES = List.of(OrderStatus.AWAITING_CONFIRMATION, OrderStatus.REJECTED,
      OrderStatus.READY_FOR_DELIVERY, OrderStatus.ACCEPTED, OrderStatus.IN_PREPARATION);

  List<OrderStatus> DELIVERY_ALLOWED_ORDER_STATUSES = List.of(OrderStatus.OUT_FOR_DELIVERY, OrderStatus.DELIVERED, OrderStatus.CANCELLED);


}
