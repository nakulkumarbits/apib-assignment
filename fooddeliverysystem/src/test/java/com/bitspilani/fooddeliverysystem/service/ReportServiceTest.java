package com.bitspilani.fooddeliverysystem.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.bitspilani.fooddeliverysystem.dto.ReportDTO;
import com.bitspilani.fooddeliverysystem.enums.OrderStatus;
import com.bitspilani.fooddeliverysystem.exceptions.InvalidRequestException;
import com.bitspilani.fooddeliverysystem.model.OrderDetail;
import com.bitspilani.fooddeliverysystem.model.Restaurant;
import com.bitspilani.fooddeliverysystem.repository.OrderDetailRepository;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ReportServiceTest {

    @Mock
    OrderDetailRepository orderDetailRepository;
    @InjectMocks
    ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGeneratePopularRestaurantsReport() {
        OrderDetail orderDetail1 = getOrderDetail();
        OrderDetail orderDetail2 = getOrderDetail();
        OrderDetail orderDetail3 = getOrderDetail();
        orderDetail3.getRestaurant().setRestaurantName(orderDetail2.getRestaurant().getRestaurantName());
        when(orderDetailRepository.findAll()).thenReturn(List.of(orderDetail1, orderDetail2, orderDetail3));

        ReportDTO result = reportService.generatePopularRestaurantsReport();
        assertNotNull(result);
    }

    @Test
    void testGenerateAverageDeliveryTimeReport() {
        when(orderDetailRepository.findByOrderStatus(any(OrderStatus.class))).thenReturn(List.of(getOrderDetail()));

        ReportDTO result = reportService.generateAverageDeliveryTimeReport();
        assertNotNull(result);
    }

    static Stream<Object[]> periodProvider() {
        return Stream.of(
            new Object[]{FoodDeliveryConstants.DAILY},
            new Object[]{FoodDeliveryConstants.WEEKLY},
            new Object[]{FoodDeliveryConstants.MONTHLY}
        );
    }

    @ParameterizedTest
    @MethodSource("periodProvider")
    void testGenerateOrderTrendsReport(String period) {
        when(orderDetailRepository.findByCreatedDateAfter(any(LocalDateTime.class))).thenReturn(
            List.of(new OrderDetail()));
        when(orderDetailRepository.findByCreatedDateAfter(any(LocalDateTime.class))).thenReturn(
            List.of(getOrderDetail()));

        ReportDTO result = reportService.generateOrderTrendsReport(period);
        assertNotNull(result);
    }

    @Test
    void testGenerateOrderTrendsReportInvalidPeriod() {
        when(orderDetailRepository.findByCreatedDateAfter(any(LocalDateTime.class))).thenReturn(
            List.of(new OrderDetail()));
        when(orderDetailRepository.findByCreatedDateAfter(any(LocalDateTime.class))).thenReturn(
            List.of(getOrderDetail()));
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
            () -> reportService.generateOrderTrendsReport("invalid period"));
        assertTrue(exception.getMessage().contains("Invalid period"));
    }

    private OrderDetail getOrderDetail() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(1L);
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(RandomStringUtils.randomAlphabetic(10));
        orderDetail.setRestaurant(restaurant);
        orderDetail.setOrderItems(new ArrayList<>());
        orderDetail.setTotalAmount(100.0);
        orderDetail.setCreatedDate(LocalDateTime.now().minusDays(2));
        orderDetail.setModifiedDate(LocalDateTime.now().minusDays(1));
        return orderDetail;
    }
}